package br.com.rocketseat.passin.controller;

import br.com.rocketseat.passin.dto.event.EventIdDTO;
import br.com.rocketseat.passin.dto.event.EventRequestDTO;
import br.com.rocketseat.passin.dto.event.EventResponseDTO;
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

    @GetMapping
    public String helloEvents() {
        return "Hello Events!";
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String id) {
        var event = eventService.getEventDetails(id);
        return ResponseEntity.ok(event);
    }

    @PostMapping
    public ResponseEntity<EventIdDTO> createEvent(
            @RequestBody EventRequestDTO eventRequestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var event = eventService.createEvent(eventRequestDTO);

        var uri = uriComponentsBuilder.path("/events/{id}")
                .buildAndExpand(event.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(event);
    }
}
