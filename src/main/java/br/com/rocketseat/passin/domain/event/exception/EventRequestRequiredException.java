package br.com.rocketseat.passin.domain.event.exception;

public class EventRequestRequiredException extends RuntimeException {

    public EventRequestRequiredException(String message) {
        super(message);
    }
}
