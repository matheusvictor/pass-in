package br.com.rocketseat.passin.service;

import br.com.rocketseat.passin.domain.attendees.Attendee;
import br.com.rocketseat.passin.domain.attendees.exception.AttendeeAlreadyExistsException;
import br.com.rocketseat.passin.domain.attendees.exception.AttendeeNotFoundException;
import br.com.rocketseat.passin.dto.attendee.AttendeeBadgeDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeDetailsDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeListResponseDTO;
import br.com.rocketseat.passin.repository.AttendeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final CheckInService checkInService;
    private final AttendeeRepository attendeeRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return attendeeRepository.findAllByEventId(eventId);
    }

    public AttendeeListResponseDTO getEventsAttendee(String eventId) {
        var attendees = attendeeRepository.findAllByEventId(eventId);

        var attendeeDetailsList = attendees
                .stream()
                .map(attendee -> {

                    var checkIn = checkInService.getCheckInByAttendeeId(attendee.getId());
                    var checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;

                    return new AttendeeDetailsDTO(
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getCreatedAt(),
                            checkedInAt
                    );
                }).toList();

        return new AttendeeListResponseDTO(attendeeDetailsList);
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(
            String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var attendee = this.getAttendeeById(attendeeId);

        var checkInUri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/check-in")
                .buildAndExpand(attendeeId)
                .toUri();

        return new AttendeeBadgeResponseDTO(
                new AttendeeBadgeDTO(
                        attendee.getName(),
                        attendee.getEmail(),
                        checkInUri.toString(),
                        attendee.getEvent().getId()
                )
        );
    }

    public Attendee registerAttendee(Attendee attendee) {
        return attendeeRepository.save(attendee);
    }

    public void verifyAttendeeSubscription(String email, String eventId) {
        var attendeeRegistered = attendeeRepository.findAllByEmailAndEventId(email, eventId);

        if (attendeeRegistered.isPresent()) {
            throw new AttendeeAlreadyExistsException("Attendee already registered");
        }
    }

    public void doCheckIn(String attendeeId) {
        var attendee = getAttendeeById(attendeeId);
        checkInService.doCheckInAttendee(attendee);
    }

    private Attendee getAttendeeById(String attendeeId) {
        return attendeeRepository
                .findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found"));
    }
}
