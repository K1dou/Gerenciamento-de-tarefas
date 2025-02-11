package com.kidou.aplicativo.de.gerenciamento.de.tarefas.config;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UserDetailsServiceClass;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserDetailsServiceClass userDetailsServiceClass;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        var token = recoverToken(request);
        if (token!=null){

         UserDetails userDetails = userDetailsServiceClass.loadUserByUsername(tokenService.validateToken(token));
         var auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);

    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");

    }
}
