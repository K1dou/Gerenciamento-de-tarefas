package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


    @Transactional
    @Modifying
    @Query("update Tarefa t set t.status = ?1 where t.id = ?2 ")
    void alteraStatus(Status status, Long id);


    @Query("select t from Tarefa t where t.status = 'ATRASADA'")
    List<Tarefa> buscaTarefasAtrasadas();

    @Query("select t from Tarefa t where t.status = 'ABERTA'")
    List<Tarefa> buscaTarefasAbertas();

    @Query("select t from Tarefa t where t.status = 'REALIZADA'")
    List<Tarefa> buscaTarefasRealizadas();

    @Query("select t from Tarefa t where t.dataDaTarefa>= ?1 and t.prazoDaTarefa<= ?2")
    List<Tarefa> buscarTarefasEntreAsDatas(LocalDateTime dataDeInicio, LocalDateTime dataDeTermino);

    @Query("select t from Tarefa t join t.user u where u.id =?1 ")
    List<Tarefa> buscaTarefasPorIdUsuario(Long idUsuario);


    @Query("select t from Tarefa t join t.user u where u.id =?1 and t.status = 'ATRASADA'")
    List<Tarefa> buscaTarefasAtrasadasPorIdUsuario(Long idUsuario);

    @Query("select t from Tarefa t join t.user u where u.id =?1 and t.status = 'ABERTA'")
    List<Tarefa> buscaTarefasAbertasPorIdUsuario(Long idUsuario);

    @Query("select t from Tarefa t join t.user u where u.id =?1 and t.status = 'REALIZADA'")
    List<Tarefa> buscaTarefasRealizadasPorIdUsuario(Long idUsuario);

    @Query("select t from Tarefa t join t.user u where t.dataDaTarefa >= ?1 and t.prazoDaTarefa <= ?2 and u.id = ?3")
    List<Tarefa> buscarTarefasEntreAsDatasPorIdUsuario(LocalDateTime inicioDoDia, LocalDateTime fimDoDia, Long idUsuario);

    @Transactional
    @Modifying
    @Query("update Tarefa t join t.user u set t.status = 'REALIZADA' where u.id =?2 and t.id =?1")
    void concluiTarefaPorIdUsuario(Long idTarefa, Long idUsuario);

    @Query("select count(1)>0 t from Tarefa t join t.user u where t.id =?1 and u.id = ?2")
    boolean tarefaIsTheUser(Long idTarefa, Long idUsuario);
}
