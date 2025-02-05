package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.config.TokenService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.UsuarioRegisterDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginRegisteUsuarioController.class)
@Import(TokenService.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginRegisteUsuarioControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UsuarioRepository usuarioRepository;


    @Test
    public void registerUsuario_WithValidData_ReturnsCreated() throws Exception {

        UsuarioRegisterDTO usuarioRegisterDT = new UsuarioRegisterDTO();
        usuarioRegisterDT.setEmail("hique1276@gmail.com");
        usuarioRegisterDT.setSenha("Marcelo2809@");

        when(usuarioService.registerUsuario(usuarioRegisterDT)).thenReturn("Usuario criado");


        mockMvc.perform(post("/v1/auth/register")
                        .content(objectMapper.writeValueAsString(usuarioRegisterDT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

    }

    @Test
    public void registerUsuario_WithBlankData_ReturnsBadRequest() throws Exception {

        UsuarioRegisterDTO usuarioRegisterDT = new UsuarioRegisterDTO();
        usuarioRegisterDT.setEmail(""); // Email vazio
        usuarioRegisterDT.setSenha(""); // Senha vazia

        mockMvc.perform(post("/v1/auth/register")
                        .content(objectMapper.writeValueAsString(usuarioRegisterDT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.email").value("Necessário informar o email"))
                .andExpect(jsonPath("$.errors.senha").value("Informe a senha"));
    }



}