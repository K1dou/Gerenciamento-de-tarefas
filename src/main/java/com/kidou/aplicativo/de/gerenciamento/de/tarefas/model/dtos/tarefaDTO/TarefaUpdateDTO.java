package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class TarefaUpdateDTO {

    @NotEmpty(message = "Informe o id da tarefa a ser editada")
    private Long id;
    private String descricao;
    private Nivel nivel;
    private Status status;
    private LocalDateTime dataDaTarefa;
    private LocalDateTime prazoDaTarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataDaTarefa() {
        return dataDaTarefa;
    }

    public void setDataDaTarefa(LocalDateTime dataDaTarefa) {
        this.dataDaTarefa = dataDaTarefa;
    }

    public LocalDateTime getPrazoDaTarefa() {
        return prazoDaTarefa;
    }

    public void setPrazoDaTarefa(LocalDateTime prazoDaTarefa) {
        this.prazoDaTarefa = prazoDaTarefa;
    }
}
