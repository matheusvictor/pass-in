package br.com.rocketseat.passin.controller;

import br.com.rocketseat.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.rocketseat.passin.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequiredArgsConstructor
@RequestMapping("/attendees")
public class AttendeeController {

    private final AttendeeService attendeeService;

    @GetMapping
    public String helloAttendees() {
        return "Hello Attendees!";
    }

    @GetMapping("/{attendeeId}/badge")
    public ResponseEntity<AttendeeBadgeResponseDTO> getAttendeeBadge(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var response = attendeeService.getAttendeeBadge(attendeeId, uriComponentsBuilder);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
