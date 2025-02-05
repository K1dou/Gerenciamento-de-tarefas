package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO;

public class UsuarioDTO {

    private Long id;
    private String email;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String email) {
        this.id = id;
        this.email = email;
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
}
