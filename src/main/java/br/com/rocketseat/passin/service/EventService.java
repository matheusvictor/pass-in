package br.com.rocketseat.passin.service;

import br.com.rocketseat.passin.domain.event.Event;
import br.com.rocketseat.passin.dto.event.EventIdDTO;
import br.com.rocketseat.passin.dto.event.EventRequestDTO;
import br.com.rocketseat.passin.dto.event.EventResponseDTO;
import br.com.rocketseat.passin.repository.AttendeeRepository;
import br.com.rocketseat.passin.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;


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

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        var newEvent = new Event();

        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(generateSlug(eventDTO.title()));

        eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    private String generateSlug(String text) {
        var normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalized
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }
}
