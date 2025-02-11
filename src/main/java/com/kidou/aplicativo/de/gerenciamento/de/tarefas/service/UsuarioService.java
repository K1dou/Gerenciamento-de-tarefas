package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.config.TokenService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioLoginDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.UsuarioDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService  {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;

    private final Logger logger = LoggerFactory.getLogger(UsuarioService.class);


    public Long obtemIdUsuario() {

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(email);
        Long idUsuario = usuario.getId();
        return idUsuario;
    }


    public List<UsuarioDTO> findAllUser() throws GerenciamentoDeTarefasException {

        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuarioDTOS = usuarios.stream().map(user -> modelMapper.map(user, UsuarioDTO.class)).collect(Collectors.toList());
        if (usuarios.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhum Usuario encontrado");
        }

        return usuarioDTOS;
    }

    public void deleteUserById(Long id) throws GerenciamentoDeTarefasException {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new GerenciamentoDeTarefasException("Usuario não encontrado"));
        usuarioRepository.delete(usuario);
    }

    public UsuarioDTO findUserByEmail(String name) {
        Usuario usuario = usuarioRepository.findByEmail(name);
        UsuarioDTO usuarioDTO = modelMapper.map(usuario, UsuarioDTO.class);

        return usuarioDTO;
    }

    public String registerUsuario(UsuarioRegisterDTO usuarioRegisterDTO) throws GerenciamentoDeTarefasException {

        Usuario user = usuarioRepository.findByEmail(usuarioRegisterDTO.getEmail());

        if (user != null) {
            throw new GerenciamentoDeTarefasException("o Usuario ja existe");
        }

        user = new Usuario();
        modelMapper.map(usuarioRegisterDTO, user);

        String senha = passwordEncoder.encode(user.getSenha());
        user.setSenha(senha);
        user.setRoles(Role.ROLE_USER);
        usuarioRepository.save(user);

        return "Usuario criado";
    }

    public String loginUsuario(UsuarioLoginDTO usuarioLoginDTO) {

        var userPass = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getEmail(), usuarioLoginDTO.getSenha());
        var auth = this.authenticationManager.authenticate(userPass);

        String token = tokenService.generateToken((Usuario) auth.getPrincipal());


        return token;
    }


}
