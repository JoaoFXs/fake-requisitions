package br.com.joaofxs.client_scheduling_microsservice.core.exception;

import br.com.joaofxs.client_scheduling_microsservice.core.dto.exception.ResponseException;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.http.HttpResponse;

@ControllerAdvice
public class CoreExceptionHandler {


    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(RuntimeException ex){
        ResponseException response = new ResponseException(HttpStatus.NOT_FOUND.name(),HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler({UserAlreadyExistException.class})
        public ResponseEntity<?> handleUserAlreadyExists(UserAlreadyExistException ex){
        ResponseException response = new ResponseException(HttpStatus.CONFLICT.name(),HttpStatus.CONFLICT.value(),ex.getMessage());
        return new ResponseEntity<>(
                response,
                HttpStatus.CONFLICT
        );
    }

}
