package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums;

public enum Status {

    REALIZADA("Realizada"),ATRASADA("Atrasada"),ABERTA("Aberta");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
