package br.com.rocketseat.passin.controller;

import br.com.rocketseat.passin.dto.attendee.AttendeeIdDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeListResponseDTO;
import br.com.rocketseat.passin.dto.attendee.AttendeeRequestDTO;
import br.com.rocketseat.passin.dto.event.EventIdDTO;
import br.com.rocketseat.passin.dto.event.EventListResponseDTO;
import br.com.rocketseat.passin.dto.event.EventRequestDTO;
import br.com.rocketseat.passin.dto.event.EventResponseDTO;
import br.com.rocketseat.passin.service.AttendeeService;
import br.com.rocketseat.passin.service.EventService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "get", description = "List all events")
    @ApiResponse(
            responseCode = "200",
            description = "List of all events. If there are no events, an empty list is returned."
    )
    public ResponseEntity<EventListResponseDTO> getAllEvents() {
        var events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{attendeeId}")
    @Tag(name = "get", description = "List an event that a specific attendee is registered")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Events details for the attendee. " +
                                    "If the attendee is not registered in any event, an empty list is returned."
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found"
                    )
            }
    )
    public ResponseEntity<EventResponseDTO> getEventDetailsByAttendeeId(
            @Parameter(
                    description = "ID of the attendee",
                    example = "2329835b-9bc8-4470-a533-cc6f0f56ab02",
                    required = true
            )
            @PathVariable String attendeeId
    ) {
        var event = eventService.getEventDetails(attendeeId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/attendees/{attendeeId}")
    @Tag(name = "get", description = "List all events that a specific attendee is registered")
    @ApiResponse(
            responseCode = "200",
            description = "List of all events that the attendee is registered. " +
                    "If the attendee is not registered in any event, an empty list is returned."
    )
    public ResponseEntity<AttendeeListResponseDTO> getEventAttendees(
            @Parameter(
                    description = "ID of the attendee",
                    example = "2329835b-9bc8-4470-a533-cc6f0f56ab02",
                    required = true
            )
            @PathVariable String attendeeId
    ) {
        var attendees = attendeeService.getEventsAttendee(attendeeId);
        return ResponseEntity.ok(attendees);
    }

    @PostMapping
    @Tag(name = "post", description = "Create a new event")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Event created successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Event already exists"
                    )
            }
    )
    public ResponseEntity<EventIdDTO> createEvent(
            @Parameter(
                    description = "Event data to be created",
                    required = true
            )
            @RequestBody EventRequestDTO eventRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var event = eventService.createEvent(eventRequestDTO);

        var uri = uriComponentsBuilder
                .path("/events/{attendeeId}")
                .buildAndExpand(event.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(event);
    }

    @PostMapping("/{eventId}/attendees")
    @Tag(name = "post", description = "Register an attendee in an event")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Attendee registered successfully"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Event not found"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Attendee already registered in the event"
                    )
            }
    )
    public ResponseEntity<AttendeeIdDTO> addAttendeeToEvent(
            @Parameter(
                    description = "ID of the event",
                    example = "2329835b-9bc8-4470-a533-cc6f0f56ab02",
                    required = true
            )
            @PathVariable String eventId,
            @Parameter(
                    description = "Attendee data to be registered",
                    required = true
            )
            @RequestBody AttendeeRequestDTO attendeeRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var attendeeIdDTO = eventService.registerAttendee(eventId, attendeeRequestDTO);

        var uri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/badge")
                .buildAndExpand(attendeeIdDTO.attendeeId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(attendeeIdDTO);
    }
}
