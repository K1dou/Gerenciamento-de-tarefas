package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Tipo;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class TarefaRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    TarefaRepository tarefaRepository;


    private Tarefa criarTarefa() {

        List<Usuario> usuarios = new ArrayList<>();

        Usuario user = new Usuario(Role.ROLE_USER, "senha", "hique1276@gmail.com");

        usuarios.add(user);

        entityManager.persist(user);


        Tarefa tarefa = new Tarefa();
        tarefa.setUser(usuarios);
        tarefa.setDescricao("testes");
        tarefa.setNivel(Nivel.BAIXO);
        tarefa.setStatus(Status.ABERTA);
        tarefa.setDataDaTarefa(LocalDateTime.now());
        tarefa.setPrazoDaTarefa(LocalDateTime.now());
        tarefa.setTipoDaTarefa(Tipo.INDIVIDUAL);

        return tarefa;
    }


    @Test
    @DisplayName("must take overdue tasks")
    void buscaTarefasAtrasadasCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);
        entityManager.persist(tarefa);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAtrasadas();

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("Should not take overdue tasks")
    void buscaTarefasAtrasadasCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAtrasadas();

        assertThat(resultado.isEmpty()).isTrue();
    }


    @Test
    @DisplayName("should get open tasks")
    void buscaTarefasAbertasCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);
        entityManager.persist(tarefa);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAbertas();

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("should not get open tasks")
    void buscaTarefasAbertasCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);
        entityManager.persist(tarefa);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAbertas();

        assertThat(resultado.isEmpty()).isTrue();
    }


    @Test
    @DisplayName("should get tasks performed")
    void buscaTarefasRealizadasCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.REALIZADA);
        entityManager.persist(tarefa);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadas();

        assertThat(resultado.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("should not get tasks performed")
    void buscaTarefasRealizadasCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.REALIZADA);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadas();

        assertThat(resultado.isEmpty()).isTrue();
    }


    @Test
    @DisplayName("should get tasks between dates")
    void buscarTarefasEntreAsDatasCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setDataDaTarefa(LocalDateTime.of(2025, 2, 10, 14, 30));
        tarefa.setPrazoDaTarefa(LocalDateTime.of(2025, 2, 20, 14, 30));
        entityManager.persist(tarefa);

        List<Tarefa> resultado = tarefaRepository.buscarTarefasEntreAsDatas(tarefa.getDataDaTarefa(), tarefa.getPrazoDaTarefa());

        assertThat(resultado.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("should not get tasks between dates")
    void buscarTarefasEntreAsDatasCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setDataDaTarefa(LocalDateTime.of(2020, 2, 10, 14, 30));
        tarefa.setPrazoDaTarefa(LocalDateTime.of(2020, 2, 20, 14, 30));

        List<Tarefa> resultado = tarefaRepository.buscarTarefasEntreAsDatas(tarefa.getDataDaTarefa(), tarefa.getPrazoDaTarefa());

        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("should get tasks by user id")
    void buscaTarefasPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);
        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        List<Tarefa> resultado = tarefaRepository.buscaTarefasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("should not get tasks by user id")
    void buscaTarefasPorIdUsuarioCase2() {

        Tarefa tarefa = criarTarefa();
        Long idUsuario = 2L;

        List<Tarefa> resultado = tarefaRepository.buscaTarefasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("should get overdue tasks by user id")
    void buscaTarefasAtrasadasPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);
        entityManager.persist(tarefa);
        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAtrasadasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("should not get overdue tasks by user id")
    void buscaTarefasAtrasadasPorIdUsuarioCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);
        Long idUsuario = 2L;


        List<Tarefa> resultado = tarefaRepository.buscaTarefasAtrasadasPorIdUsuario(2L);

        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("should get open tasks by user id")
    void buscaTarefasAbertasPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);
        entityManager.persist(tarefa);
        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        List<Tarefa> resultado = tarefaRepository.buscaTarefasAbertasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("should not get open tasks by user id")
    void buscaTarefasAbertasPorIdUsuarioCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);

        Long idUsuario = 2L;


        List<Tarefa> resultado = tarefaRepository.buscaTarefasAbertasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("should get tasks performed by user id")
    void buscaTarefasRealizadasPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.REALIZADA);
        entityManager.persist(tarefa);
        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isFalse();
    }

    @Test
    @DisplayName("should not get tasks performed by user id")
    void buscaTarefasRealizadasPorIdUsuarioCase2() {
        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.REALIZADA);
        Long idUsuario = 2L;

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("should get tasks between dates by user id")
    void buscarTarefasEntreAsDatasPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setDataDaTarefa(LocalDateTime.of(2025, 2, 10, 14, 30));
        tarefa.setPrazoDaTarefa(LocalDateTime.of(2025, 2, 20, 14, 30));
        entityManager.persist(tarefa);

        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }


        List<Tarefa> resultado = tarefaRepository.buscarTarefasEntreAsDatasPorIdUsuario(tarefa.getDataDaTarefa(), tarefa.getPrazoDaTarefa(), idUsuario);

        assertThat(resultado.isEmpty()).isFalse();


    }

    @Test
    @DisplayName("should not get tasks between dates by user id")
    void buscarTarefasEntreAsDatasPorIdUsuarioCase2() {


        Tarefa tarefa = criarTarefa();
        tarefa.setDataDaTarefa(LocalDateTime.of(2025, 2, 10, 14, 30));
        tarefa.setPrazoDaTarefa(LocalDateTime.of(2025, 2, 20, 14, 30));

        Long idUsuario = 2L;

        List<Tarefa> resultado = tarefaRepository.buscarTarefasEntreAsDatasPorIdUsuario(tarefa.getDataDaTarefa(), tarefa.getPrazoDaTarefa(), idUsuario);

        assertThat(resultado.isEmpty()).isTrue();
    }


    @Test
    @DisplayName("Must complete user task by user id")
    void concluiTarefaPorIdUsuarioCase1() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);
        entityManager.persist(tarefa);

        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        tarefaRepository.concluiTarefaPorIdUsuario(tarefa.getId(), idUsuario);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isFalse();

    }

    @Test
    @DisplayName("Must not complete user task by user id")
    void concluiTarefaPorIdUsuarioCase2() {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);

        Long idUsuario = 2L;

        tarefaRepository.concluiTarefaPorIdUsuario(tarefa.getId(), idUsuario);

        List<Tarefa> resultado = tarefaRepository.buscaTarefasRealizadasPorIdUsuario(idUsuario);

        assertThat(resultado.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("tarefaIsTheUser's success")
    void tarefaIsTheUserCase1() {

        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);

        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        boolean resultado = tarefaRepository.tarefaIsTheUser(tarefa.getId(),idUsuario);

        assertThat(resultado).isTrue();
    }

    @Test
    @DisplayName("tarefaIsTheUser's unsuccessful")
    void tarefaIsTheUserCase2() {

        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);

        Long idUsuario = 0L;

        for (Usuario user : tarefa.getUser()) {
            idUsuario = user.getId();
        }

        boolean resultado = tarefaRepository.tarefaIsTheUser(tarefa.getId(),3L);

        assertThat(resultado).isFalse();
    }
}