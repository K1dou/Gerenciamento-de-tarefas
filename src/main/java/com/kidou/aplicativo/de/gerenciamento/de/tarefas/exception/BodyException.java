package com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class BodyException {

    private String error;
    private LocalDateTime horario;
    private HttpStatus status;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
