package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.config.TokenService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioLoginDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class LoginRegisteUsuarioController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Logger logger = LoggerFactory.getLogger(LoginRegisteUsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUsuario(@RequestBody @Valid UsuarioRegisterDTO usuario) throws GerenciamentoDeTarefasException {
        Usuario user = usuarioRepository.findByEmail(usuario.getEmail());
        if (user != null) {
            throw new GerenciamentoDeTarefasException("o Usuario ja existe");
        }
        user = new Usuario();
        modelMapper.map(usuario, user);

        String senha = passwordEncoder.encode(user.getSenha());
        user.setSenha(senha);
        usuarioRepository.save(user);
        return new ResponseEntity<String>("Usuario criado", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestBody @Valid UsuarioLoginDTO usuario) throws GerenciamentoDeTarefasException {
        var userPass = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
        var auth = this.authenticationManager.authenticate(userPass);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());

        logger.info("Saindo no login do usuario");

        return new ResponseEntity<String>(token, HttpStatus.OK);
    }


}
