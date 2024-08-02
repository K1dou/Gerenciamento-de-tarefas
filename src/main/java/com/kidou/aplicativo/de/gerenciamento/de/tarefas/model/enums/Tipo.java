package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums;

public enum Tipo {

    EM_GRUPO("EmGrupo"),INDIVIDUAL("Individual");

    private String value;

    Tipo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
