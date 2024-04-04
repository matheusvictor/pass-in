package br.com.rocketseat.passin.controller;

import br.com.rocketseat.passin.dto.attendee.AttendeeIdDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeListResponseDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeRequestDTO;
import br.com.rocketseat.passin.dto.event.EventIdDTO;
import br.com.rocketseat.passin.dto.event.EventRequestDTO;
import br.com.rocketseat.passin.dto.event.EventResponseDTO;
import br.com.rocketseat.passin.service.AttendeeService;
import br.com.rocketseat.passin.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final AttendeeService attendeeService;

    @GetMapping
    public String helloEvents() {
        return "Hello Events!";
    }

    @GetMapping("/{attendeeId}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String attendeeId) {
        var event = eventService.getEventDetails(attendeeId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/attendees/{attendeeId}")
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(@PathVariable String attendeeId) {
        var attendees = attendeeService.getEventsAttendee(attendeeId);
        return ResponseEntity.ok(attendees);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(
            @RequestBody EventRequestDTO eventRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var event = eventService.createEvent(eventRequestDTO);

        var uri = uriComponentsBuilder.path("/events/{attendeeId}")
                .buildAndExpand(event.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(event);
    }

    @PostMapping("/{eventId}/attendees")
    public ResponseEntity<AttendeeIdDTO> addAttendeeToEvent(
            @PathVariable String eventId,
            @RequestBody AttendeeRequestDTO attendeeRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var attendeeIdDTO = eventService.registerAttendee(eventId, attendeeRequestDTO);

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/badge")
                .buildAndExpand(attendeeIdDTO.attendeeId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(attendeeIdDTO);
    }
}
