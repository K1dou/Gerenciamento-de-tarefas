package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums;

public enum Role {
    ROLE_ADMIN("Admin"),ROLE_USER("User");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
