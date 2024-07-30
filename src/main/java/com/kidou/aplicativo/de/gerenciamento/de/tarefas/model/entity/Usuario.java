package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Email
    @Column(unique = true,nullable = false)
    private String email;
    private String senha;

    @OneToMany(mappedBy = "user")
    private List<Tarefa>tarefas = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role roles;

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}
