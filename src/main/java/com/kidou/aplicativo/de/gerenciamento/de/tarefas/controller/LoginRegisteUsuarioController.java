package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class LoginRegisteUsuarioController {

    private final Logger logger = LoggerFactory.getLogger(LoginRegisteUsuarioController.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<Void> registerUsuario(@RequestBody @Valid UsuarioRegisterDTO usuario) throws GerenciamentoDeTarefasException {

        logger.info("Criando usuario...");
        String senha = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senha);
        usuarioService.salvaUsuario(usuario);
        logger.info("Usuario criado");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
