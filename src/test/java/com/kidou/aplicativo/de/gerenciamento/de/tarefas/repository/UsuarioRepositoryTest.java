package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class UsuarioRepositoryTest {


    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    @DisplayName("deve retornar usuario com sucesso")
    void findByEmailCase1() {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "hique1276@gmail.com");

        entityManager.persist(usuario);

        Usuario resultado = usuarioRepository.findByEmail("hique1276@gmail.com");

        assertThat(resultado).isNotNull();

    }

    @Test
    @DisplayName("nao deve retornar usuario")
    void findByEmailCase2() {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "hique1276@gmail.com");

        entityManager.persist(usuario);

        Usuario resultado = usuarioRepository.findByEmail("hique@gmail.com");

        assertThat(resultado).isNull();

    }

}