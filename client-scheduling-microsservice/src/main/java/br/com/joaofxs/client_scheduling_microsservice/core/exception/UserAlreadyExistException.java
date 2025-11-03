package br.com.joaofxs.client_scheduling_microsservice.core.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
