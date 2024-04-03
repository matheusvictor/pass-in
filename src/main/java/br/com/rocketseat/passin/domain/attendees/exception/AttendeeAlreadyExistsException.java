package br.com.rocketseat.passin.domain.attendees.exception;

public class AttendeeAlreadyExistsException extends RuntimeException {

    public AttendeeAlreadyExistsException(String message) {
        super(message);
    }
}
