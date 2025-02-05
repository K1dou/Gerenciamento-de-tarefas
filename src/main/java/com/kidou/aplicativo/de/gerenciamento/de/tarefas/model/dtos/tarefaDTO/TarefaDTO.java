package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Tipo;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;

public class TarefaDTO {

    private Long id;

    @NotEmpty(message = "Necessario informar o id do usuario")
    private List<UsuarioDTO> user;
    @NotEmpty(message = "Necessario descriçao da sua tarefa")
    private String descricao;
    private Nivel nivel;
    @NotEmpty(message = "Informe o nivel da Tarefa")
    private Status status = Status.ABERTA;

    @NotEmpty(message = "Informe a data para o inicio da tarefa")
    private LocalDateTime dataDaTarefa;
    @NotEmpty(message = "Informe a data para o fim da tarefa")
    private LocalDateTime prazoDaTarefa;

    private Tipo tipoDaTarefa = Tipo.INDIVIDUAL;

    public TarefaDTO() {
    }

    public TarefaDTO(List<UsuarioDTO> user, String descricao, Nivel nivel, LocalDateTime dataDaTarefa, LocalDateTime prazoDaTarefa) {
        this.user = user;
        this.descricao = descricao;
        this.nivel = nivel;
        this.dataDaTarefa = dataDaTarefa;
        this.prazoDaTarefa = prazoDaTarefa;
    }

    public Tipo getTipoDaTarefa() {
        return tipoDaTarefa;
    }

    public void setTipoDaTarefa(Tipo tipoDaTarefa) {
        this.tipoDaTarefa = tipoDaTarefa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UsuarioDTO> getUser() {
        return user;
    }

    public void setUser(List<UsuarioDTO> user) {
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
