package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tarefa")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "user_fk"))
    private Usuario user;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataDaTarefa;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date prazoDaTarefa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
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

    public Date getDataDaTarefa() {
        return dataDaTarefa;
    }

    public void setDataDaTarefa(Date dataDaTarefa) {
        this.dataDaTarefa = dataDaTarefa;
    }

    public Date getPrazoDaTarefa() {
        return prazoDaTarefa;
    }

    public void setPrazoDaTarefa(Date prazoDaTarefa) {
        this.prazoDaTarefa = prazoDaTarefa;
    }
}
