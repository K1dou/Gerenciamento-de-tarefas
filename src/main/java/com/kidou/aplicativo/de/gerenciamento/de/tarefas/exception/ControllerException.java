package com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerException {



    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<BodyException> usernameNotFoundException(UsernameNotFoundException usernameNotFoundException){
    BodyException bodyException = new BodyException();
    bodyException.setError(usernameNotFoundException.getMessage());
    bodyException.setStatus(HttpStatus.BAD_REQUEST);
    bodyException.setHorario(LocalDateTime.now());
    return new ResponseEntity<BodyException>(bodyException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GerenciamentoDeTarefasException.class)
    public ResponseEntity<BodyException> gerenciamentoDeTarefasException(GerenciamentoDeTarefasException gerenciamentoDeTarefasException){
        BodyException bodyException = new BodyException();
        bodyException.setError(gerenciamentoDeTarefasException.getMessage());
        bodyException.setStatus(HttpStatus.BAD_REQUEST);
        bodyException.setHorario(LocalDateTime.now());
        return new ResponseEntity<BodyException>(bodyException,HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
    }

    }
