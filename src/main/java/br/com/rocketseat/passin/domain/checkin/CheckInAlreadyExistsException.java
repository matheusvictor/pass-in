package br.com.rocketseat.passin.domain.checkin;

public class CheckInAlreadyExistsException extends RuntimeException {

    public CheckInAlreadyExistsException(String message) {
        super(message);
    }
}
