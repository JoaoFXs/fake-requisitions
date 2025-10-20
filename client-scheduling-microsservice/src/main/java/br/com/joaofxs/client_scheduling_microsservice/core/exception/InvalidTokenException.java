package br.com.joaofxs.client_scheduling_microsservice.core.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
