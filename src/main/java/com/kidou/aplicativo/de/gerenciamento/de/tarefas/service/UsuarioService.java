package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper modelMapper;


    public void salvaUsuario(UsuarioRegisterDTO usuario) {
        Usuario user = usuarioRepository.findByEmail(usuario.getEmail());
        if (user!=null){
            throw new RuntimeException("o Usuario ja existe");
        }
        user= new Usuario();
        modelMapper.map(usuario, user);
        usuarioRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

       Usuario usuario = usuarioRepository.findByEmail(email);
       if (usuario==null){
           throw new UsernameNotFoundException("Usuario com email:"+email+",Não encontrado");
       }
        return usuario;
    }
}
