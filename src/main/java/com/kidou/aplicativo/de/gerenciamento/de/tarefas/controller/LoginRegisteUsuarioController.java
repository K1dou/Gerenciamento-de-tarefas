package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.config.TokenService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioLoginDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginRegisteUsuarioController {

    private final Logger logger = LoggerFactory.getLogger(LoginRegisteUsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUsuario(@RequestBody @Valid UsuarioRegisterDTO usuario) throws GerenciamentoDeTarefasException {

        String userCriado = usuarioService.registerUsuario(usuario);

        return new ResponseEntity<String>(userCriado, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody @Valid UsuarioLoginDTO usuario) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<String>(usuarioService.loginUsuario(usuario), HttpStatus.OK);
    }


}
