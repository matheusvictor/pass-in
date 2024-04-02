package br.com.rocketseat.passin.service;

import br.com.rocketseat.passin.dto.event.EventResponseDTO;
import br.com.rocketseat.passin.repository.AttendeeRepository;
import br.com.rocketseat.passin.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final AttendeeRepository attendeeRepository;

    public EventResponseDTO getEventDetails(String eventId) {

        var event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new RuntimeException(String.format("Event with ID %s not found", eventId)));
        var attendeeList = attendeeRepository.findAllByEventId(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

}
