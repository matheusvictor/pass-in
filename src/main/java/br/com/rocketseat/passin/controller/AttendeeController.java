package br.com.rocketseat.passin.controller;

import br.com.rocketseat.passin.dto.attendee.AttendeeBadgeResponseDTO;
import br.com.rocketseat.passin.dto.checkin.CheckInResponseDTO;
import br.com.rocketseat.passin.service.AttendeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/{attendeeId}/check-in")
    public ResponseEntity<CheckInResponseDTO> doCheckIn(
            @PathVariable String attendeeId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        attendeeService.doCheckIn(attendeeId);

        var checkInUri = uriComponentsBuilder
                .path("/attendees/{attendeeId}/badge")
                .buildAndExpand(attendeeId)
                .toUri();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CheckInResponseDTO(checkInUri));
    }
}
