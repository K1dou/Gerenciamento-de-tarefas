package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

public class CriaTarefaDTO {

    private Long id;

    @NotEmpty(message = "Necessario informar o id do usuario")
    private UsuarioDTO user;
    @NotEmpty(message = "Necessario descriçao da sua tarefa")
    private String descricao;
    private Nivel nivel;
    @NotEmpty(message = "Informe o nivel da Tarefa")
    private Status status = Status.ABERTA;

    @NotEmpty(message = "Informe a data para o inicio da tarefa")
    private LocalDateTime dataDaTarefa;
    @NotEmpty(message = "Informe a data para o fim da tarefa")
    private LocalDateTime prazoDaTarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDTO getUser() {
        return user;
    }

    public void setUser(UsuarioDTO user) {
        this.user = user;
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
