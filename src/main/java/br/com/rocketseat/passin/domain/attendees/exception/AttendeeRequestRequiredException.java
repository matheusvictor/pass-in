package br.com.rocketseat.passin.domain.attendees.exception;

public class AttendeeRequestRequiredException extends RuntimeException {

    public AttendeeRequestRequiredException(String message) {
        super(message);
    }
}
