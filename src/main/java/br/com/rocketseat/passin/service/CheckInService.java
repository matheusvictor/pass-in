package br.com.rocketseat.passin.service;

import br.com.rocketseat.passin.domain.attendees.Attendee;
import br.com.rocketseat.passin.domain.checkin.CheckIn;
import br.com.rocketseat.passin.domain.checkin.exception.CheckInAlreadyExistsException;
import br.com.rocketseat.passin.repository.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CheckInService {

    private final CheckInRepository checkInRepository;

    public void doCheckInAttendee(Attendee attendee) {

        checkIfAttendeeAlreadyCheckedIn(attendee.getId());

        var checkIn = new CheckIn();
        checkIn.setAttendee(attendee);
        checkIn.setCreatedAt(LocalDateTime.now());

        checkInRepository.save(checkIn);
    }

    private void checkIfAttendeeAlreadyCheckedIn(String attendeeId) {
        var checkIn = this.getCheckInByAttendeeId(attendeeId);

        if (checkIn.isPresent()) {
            throw new CheckInAlreadyExistsException("Attendee already checked in");
        }
    }

    public Optional<CheckIn> getCheckInByAttendeeId(String attendeeId) {
        return checkInRepository.findByAttendeeId(attendeeId);
    }
}
