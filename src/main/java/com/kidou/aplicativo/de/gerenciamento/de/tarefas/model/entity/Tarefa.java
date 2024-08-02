package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Tipo;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "tarefa")
public class Tarefa {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "users_tarefas",joinColumns = @JoinColumn(name = "tarefa_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Usuario> user;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataDaTarefa;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime prazoDaTarefa;

    @Enumerated(EnumType.STRING)
    private Tipo tipoDaTarefa;


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

    public List<Usuario> getUser() {
        return user;
    }

    public void setUser(List<Usuario> user) {
        this.user = user;
    }

    //    public Usuario getUser() {
//        return user;
//    }
//
//    public void setUser(Usuario user) {
//        this.user = user;
//    }

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
