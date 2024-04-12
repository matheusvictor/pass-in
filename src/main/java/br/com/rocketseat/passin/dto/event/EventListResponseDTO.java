package br.com.rocketseat.passin.dto.event;

import java.util.List;


public record EventListResponseDTO(List<EventDetailsDTO> events) {
}
