package com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

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


    }
