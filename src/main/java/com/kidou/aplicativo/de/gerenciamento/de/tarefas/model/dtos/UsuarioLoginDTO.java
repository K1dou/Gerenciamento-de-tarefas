package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class UsuarioLoginDTO {

    @NotEmpty(message = "Informe o Email")
    @Email(message = "Formato de email invalido")
    private String email;
    @NotEmpty(message = "Informe sua senha!")
    private String senha;

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
}
