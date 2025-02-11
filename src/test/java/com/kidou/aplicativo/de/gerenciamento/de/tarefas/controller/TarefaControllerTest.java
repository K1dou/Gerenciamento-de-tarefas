package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.config.TokenService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaCompatilhadaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.UsuarioDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Tipo;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.TarefaRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.TarefaService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TarefaController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import({TokenService.class})
class TarefaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TarefaService tarefaService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private TarefaRepository tarefaRepository;

    @MockBean
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ObjectMapper objectMapper;

    List<Tarefa> tarefas = new ArrayList<>();
    List<Usuario> usuarios = new ArrayList<>();

    List<TarefaDTO> tarefaDTOS = new ArrayList<>();


    List<UsuarioDTO> listUserCompartilhada = new ArrayList<>();
    TarefaCompatilhadaDTO tarefaCompatilhadaDTO = new TarefaCompatilhadaDTO(listUserCompartilhada, "TarefaCompartilhada", Nivel.ELEVADO, LocalDateTime.now(), LocalDateTime.now());

    List<UsuarioDTO> listUserSolo = new ArrayList<>();
    TarefaDTO tarefaDTOSolo = new TarefaDTO(listUserSolo, "Tarefa", Nivel.BAIXO,Status.ATRASADA, LocalDateTime.now(), LocalDateTime.now());


    @BeforeEach
    void data() {

        UsuarioDTO usuarioDTO1 = new UsuarioDTO(7L, "marcelo@gmail.com");

        UsuarioDTO usuarioDTO2 = new UsuarioDTO(5L, "hique1276@gmail.com");

        Tarefa tarefa1 = new Tarefa(1L, usuarios, "tarefa1", Nivel.ELEVADO, Status.REALIZADA, LocalDateTime.now(), LocalDateTime.now(), Tipo.INDIVIDUAL);
        Tarefa tarefa2 = new Tarefa(2L, usuarios, "tarefa2", Nivel.ELEVADO, Status.REALIZADA, LocalDateTime.now(), LocalDateTime.now(), Tipo.INDIVIDUAL);


        tarefas.add(tarefa1);
        tarefas.add(tarefa2);

        listUserCompartilhada.add(usuarioDTO1);
        listUserCompartilhada.add(usuarioDTO2);

        listUserSolo.add(usuarioDTO2);

        tarefaDTOS.add(tarefaDTOSolo);
    }

    @Test
    @Order(1)
    void criaTarefa_WithValidData_ReturnsCreated() throws Exception {

        when(tarefaService.criaTarefa(tarefaDTOSolo)).thenReturn(tarefaDTOSolo);

        mockMvc.perform(post("/v1/tarefa/criaTarefa")
                        .content(objectMapper.writeValueAsString(tarefaDTOSolo))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }


    @Test
    void criaTarefaCompatilhada() throws Exception {

        when(tarefaService.criaTarefaCompatilhada(tarefaCompatilhadaDTO)).thenReturn(tarefaCompatilhadaDTO);

        mockMvc.perform(post("/v1/tarefa/criaTarefaCompatilhada")
                        .content(objectMapper.writeValueAsString(tarefaCompatilhadaDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated());

    }

    @Test
    void buscaTarefas() throws Exception {

        when(tarefaService.buscaTarefas()).thenReturn(tarefaDTOS);

        mockMvc.perform(get("/v1/tarefa/buscaTarefas")
                )
                .andExpect(status().isOk());
    }

    @Test
    void buscaTarefasAtrasadas() throws Exception {

        when(tarefaService.buscaTarefasAtrasadas()).thenReturn(tarefaDTOS);

        mockMvc.perform(get("/v1/tarefa/buscarTarefasAtrasdas"))
                .andExpect(status().isOk());

    }

    @Test
    void buscarTarefasAbertas() throws Exception {

        when(tarefaService.buscarTarefasAbertas()).thenReturn(tarefaDTOS);


        mockMvc.perform(get("/v1/tarefa/buscarTarefasAbertas"))
                .andExpect(status().isOk());
    }

    @Test
    void buscaTarefasRealizadas() {
    }

    @Test
    void buscarTarefasEntreAsDatas() {
    }

    @Test
    void editaTarefa() {
    }

    @Test
    void deleteTarefaById() {
    }

    @Test
    void concluiTarefa() {
    }

    @Test
    void buscaTarefasAbertas() {
    }

    @Test
    void buscaTarefasPorIdUsuario() {
    }

    @Test
    void buscaTarefasAtrasadasPorIdUsuario() {
    }

    @Test
    void buscaTarefasAbertasPorIdUsuario() {
    }

    @Test
    void buscaTarefasRealizadasPorIdUsuario() {
    }

    @Test
    void buscarTarefasEntreAsDatasPorIdUsuario() {
    }

    @Test
    void concluiTarefaPorIdUsuario() {
    }

    @Test
    void deleteTarefaByIdUsuario() {
    }
}