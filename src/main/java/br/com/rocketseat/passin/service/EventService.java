package br.com.rocketseat.passin.service;

import br.com.rocketseat.passin.domain.attendees.Attendee;
import br.com.rocketseat.passin.domain.attendees.exception.AttendeeRequestRequiredException;
import br.com.rocketseat.passin.domain.event.Event;
import br.com.rocketseat.passin.domain.event.exception.EventFullException;
import br.com.rocketseat.passin.domain.event.exception.EventNotFoundException;
import br.com.rocketseat.passin.domain.event.exception.EventRequestRequiredException;
import br.com.rocketseat.passin.dto.attendee.AttendeeIdDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeRequestDTO;
import br.com.rocketseat.passin.dto.event.*;
import br.com.rocketseat.passin.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final AttendeeService attendeeService;

    public EventResponseDTO getEventDetails(String eventId) {

        var event = getEventById(eventId);
        var attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);

        return new EventResponseDTO(event, attendeeList.size());
    }

    public EventIdDTO createEvent(EventRequestDTO eventDTO) {
        this.validEventRequestDTO(eventDTO);

        var newEvent = new Event();
        newEvent.setTitle(eventDTO.title());
        newEvent.setDetails(eventDTO.details());
        newEvent.setMaximumAttendees(eventDTO.maximumAttendees());
        newEvent.setSlug(generateSlug(eventDTO.title()));

        eventRepository.save(newEvent);

        return new EventIdDTO(newEvent.getId());
    }

    public AttendeeIdDTO registerAttendee(String eventId, AttendeeRequestDTO attendeeDTO) {
        attendeeService.verifyAttendeeSubscription(attendeeDTO.email(), eventId);

        var event = getEventById(eventId);

        var attendeeList = attendeeService.getAllAttendeesFromEvent(eventId);

        if (event.getMaximumAttendees() <= attendeeList.size()) {
            throw new EventFullException("Event is full");
        }

        this.validAttendeeRequestDTO(attendeeDTO);

        var newAttendee = new Attendee();
        newAttendee.setName(attendeeDTO.name());
        newAttendee.setEmail(attendeeDTO.email());
        newAttendee.setEvent(event);
        newAttendee.setCreatedAt(LocalDateTime.now());

        attendeeService.registerAttendee(newAttendee);
        return new AttendeeIdDTO(newAttendee.getId());
    }

    public EventListResponseDTO getAllEvents() {
        var events = eventRepository.findAll();

        var eventDetailsList = events
                .stream()
                .map(event -> new EventDetailsDTO(
                        event.getId(),
                        event.getTitle(),
                        event.getDetails(),
                        event.getSlug(),
                        event.getMaximumAttendees(),
                        attendeeService.getAllAttendeesFromEvent(event.getId()).size()
                )).toList();

        return new EventListResponseDTO(eventDetailsList);
    }

    private String generateSlug(String text) {
        var normalized = Normalizer.normalize(text, Normalizer.Form.NFD);

        return normalized
                .replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}", "")
                .replaceAll("[^\\w\\s]", "")
                .replaceAll("\\s+", "-")
                .toLowerCase();
    }

    private Event getEventById(String eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format("Event with ID %s not found", eventId)));
    }

    private void validEventRequestDTO(EventRequestDTO eventDTO) {

        if (eventDTO == null) {
            throw new EventRequestRequiredException("Event request is required");
        }

        if (eventDTO.title().isEmpty() || eventDTO.details() == null || eventDTO.maximumAttendees() == null) {
            throw new EventRequestRequiredException("Title, details and maximum attendees are required");
        }
    }

    private void validAttendeeRequestDTO(AttendeeRequestDTO attendeeDTO) {
        if (attendeeDTO == null) {
            throw new AttendeeRequestRequiredException("Attendee request is required");
        }

        if (attendeeDTO.name().isEmpty() || attendeeDTO.email().isEmpty()) {
            throw new AttendeeRequestRequiredException("Name and email are required");
        }
    }
}
