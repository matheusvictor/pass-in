package br.com.rocketseat.passin.domain.event.exception;

public class EventAlreadyExistsException extends RuntimeException {

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}
