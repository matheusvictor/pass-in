package br.com.rocketseat.passin.dto.attendee;

import java.util.List;


public record AttendeeListResponseDTO(List<AttendeeDetailsDTO> attendees) {
}
