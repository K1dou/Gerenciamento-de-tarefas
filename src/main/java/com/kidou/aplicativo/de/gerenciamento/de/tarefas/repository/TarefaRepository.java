package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {
}
