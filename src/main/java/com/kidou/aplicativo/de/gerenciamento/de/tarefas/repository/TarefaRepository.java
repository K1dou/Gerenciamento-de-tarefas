package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa,Long> {



    @Transactional
    @Modifying
    @Query("update Tarefa t set t.status = ?1 where t.id = ?2 ")
    void alteraStatus(Status status,Long id);


    @Query("select t from Tarefa t where t.status = 'ATRASADA'")
    List<Tarefa> buscaTarefasAtrasadas();

    @Query("select t from Tarefa t where t.status = 'ABERTA'")
    List<Tarefa> buscaTarefasAbertas();
}
