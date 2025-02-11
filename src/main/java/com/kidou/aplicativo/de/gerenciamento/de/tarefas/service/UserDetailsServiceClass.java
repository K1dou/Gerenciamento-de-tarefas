package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceClass implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            try {
                throw new GerenciamentoDeTarefasException("Usuario com email:" + email + ", Não encontrado");
            } catch (GerenciamentoDeTarefasException e) {
                throw new RuntimeException(e);
            }
        }
        return usuario;
    }
}
