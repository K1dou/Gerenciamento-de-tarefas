package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioRegisterDTO {

    @Email(message = "Formato de email invalido")
    @NotBlank(message = "Necessário informar o email")
    private String email;
    @NotBlank(message = "Informe a senha")
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
