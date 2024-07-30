package com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums;

public enum Nivel {

    ELEVADO("Elevado"),MEDIO("Medio"),BAIXO("Baixo");

    private String nivel;

    Nivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }
}
