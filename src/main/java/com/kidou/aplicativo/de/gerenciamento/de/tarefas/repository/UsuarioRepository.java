package com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    @Query("select u from Usuario u where u.email =?1")
    Usuario findByEmail(String email);

    @Query("select u From Usuario u where u.email like %?1%")
    List<Usuario> findUserByEmail(String name);
}
