package br.com.rocketseat.passin.config;

import br.com.rocketseat.passin.domain.attendees.exception.AttendeeAlreadyExistsException;
import br.com.rocketseat.passin.domain.attendees.exception.AttendeeNotFoundException;
import br.com.rocketseat.passin.domain.checkin.exception.CheckInAlreadyExistsException;
import br.com.rocketseat.passin.domain.event.exception.EventAlreadyExistsException;
import br.com.rocketseat.passin.domain.event.exception.EventFullException;
import br.com.rocketseat.passin.domain.event.exception.EventNotFoundException;
import br.com.rocketseat.passin.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Captura as exceções lançadas pelos controllers
public class ExceptionEntityHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventNotFound(EventNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(EventAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventAlreadyExists(EventAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(EventFullException.class)
    public ResponseEntity<ErrorResponseDTO> handleEventFull(EventFullException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(AttendeeNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAttendeeNotFound(AttendeeNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(AttendeeAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleAttendeeAlreadyExists(AttendeeAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(CheckInAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleCheckInAlreadyExists(CheckInAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(e.getMessage()));
    }
}
