package br.com.rocketseat.passin.dto.event;

public record EventDetailsDTO(
        String id,
        String title,
        String details,
        String slug,
        Integer maximumAttendees,
        Integer attendeesAmount
) {
}
