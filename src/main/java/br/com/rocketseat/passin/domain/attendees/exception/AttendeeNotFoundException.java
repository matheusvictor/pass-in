package br.com.rocketseat.passin.domain.attendees.exception;

public class AttendeeNotFoundException extends RuntimeException {

    public AttendeeNotFoundException(String message) {
        super(message);
    }
}
