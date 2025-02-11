package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaCompatilhadaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.UsuarioDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Nivel;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Tipo;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.TarefaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TarefaServiceTest {

    @Autowired
    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }


    private Tarefa criarTarefa() {

        List<Usuario> usuarios = new ArrayList<>();

        Usuario user = new Usuario(Role.ROLE_USER, "senha", "hique1276@gmail.com");

        user.setId(2L);
        usuarios.add(user);

        Tarefa tarefa = new Tarefa();
        tarefa.setUser(usuarios);
        tarefa.setDescricao("testes");
        tarefa.setNivel(Nivel.BAIXO);
        tarefa.setStatus(Status.ABERTA);
        tarefa.setDataDaTarefa(LocalDateTime.now());
        tarefa.setPrazoDaTarefa(LocalDateTime.now());
        tarefa.setTipoDaTarefa(Tipo.INDIVIDUAL);

        return tarefa;
    }


    @Test
    @DisplayName("Deve retornar lista de tarefas quando houver tarefas cadastradas para o usuário")
    void buscaTarefasPorIdUsuarioCase1() throws GerenciamentoDeTarefasException, ParseException {
        List<Tarefa> tarefas = new ArrayList<>();
        Tarefa tarefa = criarTarefa();
        tarefas.add(tarefa);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasPorIdUsuario(2L)).thenReturn(tarefas);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class)))
                .thenReturn(new TarefaDTO());

        List<TarefaDTO> resultado = tarefaService.buscaTarefasPorIdUsuario();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(tarefaRepository, times(1)).buscaTarefasPorIdUsuario(2L);
        verify(modelMapper, times(1)).map(any(Tarefa.class), eq(TarefaDTO.class));


    }

    @Test
    @DisplayName("Deve lançar exceção quando nenhuma tarefa for encontrada para o usuário")
    void buscaTarefasPorIdUsuarioCase2() {
        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasPorIdUsuario(2L)).thenReturn(Collections.emptyList());
        assertThrows(GerenciamentoDeTarefasException.class, () -> {
            tarefaService.buscaTarefasPorIdUsuario();
        });

        verify(tarefaRepository, times(1)).buscaTarefasPorIdUsuario(2L);
    }


    @Test
    @DisplayName("criar tarefa se estiver tudo okay e retornar tarefaDTO")
    void criaTarefaCase1() throws GerenciamentoDeTarefasException {
        UsuarioDTO usuario = new UsuarioDTO(1L, "hique1276@gmail.com");
        List<UsuarioDTO> usuarioList = new ArrayList<>();
        usuarioList.add(usuario);

        TarefaDTO tarefaDTO = new TarefaDTO(usuarioList, "descricao", Nivel.BAIXO, Status.ATRASADA, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5));

        Tarefa tarefaMock = new Tarefa();
        tarefaMock.setDescricao("descricao");

        when(modelMapper.map(any(TarefaDTO.class), eq(Tarefa.class)))
                .thenReturn(tarefaMock);


        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class)))
                .thenReturn(tarefaDTO);

        TarefaDTO resultado = tarefaService.criaTarefa(tarefaDTO);

        verify(tarefaRepository).save(any(Tarefa.class));
        assertNotNull(resultado);
        assertEquals("descricao", resultado.getDescricao());

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar uma tarefa com mais de um usuário")
    void criaTarefaCase2() {

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "usuario1@gmail.com");
        UsuarioDTO usuario2 = new UsuarioDTO(2L, "usuario2@gmail.com");

        List<UsuarioDTO> usuarioList = Arrays.asList(usuario1, usuario2);

        TarefaDTO tarefaDTO = new TarefaDTO(usuarioList, "descricao", Nivel.BAIXO, Status.ATRASADA, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(5));

        GerenciamentoDeTarefasException exception = assertThrows(
                GerenciamentoDeTarefasException.class,
                () -> tarefaService.criaTarefa(tarefaDTO)
        );

        assertEquals("informe apenas um Usuario", exception.getMessage());

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar uma tarefa com data anterior ao dia atual")
    void criaTarefaCase3() {

        UsuarioDTO usuario = new UsuarioDTO(1L, "usuario@gmail.com");
        List<UsuarioDTO> usuarioList = Collections.singletonList(usuario);

        TarefaDTO tarefaDTO = new TarefaDTO(usuarioList, "descricao", Nivel.BAIXO, Status.ATRASADA, LocalDateTime.now().minusDays(1), LocalDateTime.now().plusDays(5));

        GerenciamentoDeTarefasException exception = assertThrows(
                GerenciamentoDeTarefasException.class,
                () -> tarefaService.criaTarefa(tarefaDTO)
        );

        assertEquals("Não é possivel criar uma tarefa para um dia anterior a hoje!", exception.getMessage());

    }


    @Test
    @DisplayName("deve criar uma tarefa compartilhada e retornar ela com sucesso")
    void criaTarefaCompatilhadaCase1() throws GerenciamentoDeTarefasException {
        UsuarioDTO usuario1 = new UsuarioDTO(1L, "usuario1@gmail.com");
        UsuarioDTO usuario2 = new UsuarioDTO(2L, "usuario2@gmail.com");

        List<UsuarioDTO> usuarioDTOS = Arrays.asList(usuario1, usuario2);
        TarefaCompatilhadaDTO tarefaCompatilhadaDTO = new TarefaCompatilhadaDTO(usuarioDTOS, "descricao", Nivel.BAIXO, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        Tarefa tarefaMock = new Tarefa();
        tarefaMock.setDescricao("descricao");

        when(modelMapper.map(any(TarefaCompatilhadaDTO.class), eq(Tarefa.class)))
                .thenReturn(tarefaMock);

        when(modelMapper.map(any(Tarefa.class), eq(TarefaCompatilhadaDTO.class)))
                .thenReturn(tarefaCompatilhadaDTO);

        TarefaCompatilhadaDTO resultado = tarefaService.criaTarefaCompatilhada(tarefaCompatilhadaDTO);


        Assertions.assertThat(resultado).isNotNull();
        assertEquals("descricao", resultado.getDescricao());
        verify(tarefaRepository).save(any(Tarefa.class));

    }

    @Test
    @DisplayName("deve lançar uma exception quando criar uma tarefa para uma data anterior ao dia atual")
    void criaTarefaCompatilhadaCase2() throws GerenciamentoDeTarefasException {
        UsuarioDTO usuario1 = new UsuarioDTO(1L, "usuario1@gmail.com");
        UsuarioDTO usuario2 = new UsuarioDTO(2L, "usuario2@gmail.com");

        List<UsuarioDTO> usuarioDTOS = Arrays.asList(usuario1, usuario2);
        TarefaCompatilhadaDTO tarefaCompatilhadaDTO = new TarefaCompatilhadaDTO(usuarioDTOS, "descricao", Nivel.BAIXO, LocalDateTime.now().minusDays(2), LocalDateTime.now().plusDays(2));


        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                () -> tarefaService.criaTarefaCompatilhada(tarefaCompatilhadaDTO));

        assertEquals("Não é possivel criar uma tarefa para um dia anterior a hoje!", exception.getMessage());
        verifyNoInteractions(tarefaRepository);

    }

    @Test
    @DisplayName("Deve buscar tarefas e retornar uma lista de TarefaDTO")
    void buscaTarefasCase1() throws GerenciamentoDeTarefasException, ParseException {
        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "@gmail.com");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        Tarefa tarefa = new Tarefa(1L, usuarios, "descricao", Nivel.BAIXO, Status.ABERTA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Tipo.INDIVIDUAL);
        TarefaDTO tarefaDTO = new TarefaDTO(Collections.singletonList(new UsuarioDTO(1L, "usuario@gmail.com")), "descricao", Nivel.BAIXO, Status.ABERTA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2));

        when(tarefaRepository.findAll()).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);


        List<TarefaDTO> resultado = tarefaService.buscaTarefas();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("descricao", resultado.get(0).getDescricao());
        verify(tarefaRepository).findAll();
    }


    @Test
    @DisplayName("deve lançar uma exception caso a lista de tarefa retornada seja vazia")
    void buscaTarefasCase2() {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "@gmail.com");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        when(tarefaRepository.findAll()).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class, () -> tarefaService.buscaTarefas());

        assertEquals("Nenhuma tarefa encotrada", exception.getMessage());

    }


    @Test
    @DisplayName("Deve retornar a lista de tarefas atrasadas corretamente")
    void buscaTarefasAtrasadasCase1() throws GerenciamentoDeTarefasException {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "@gmail.com");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        Tarefa tarefa = new Tarefa(1L, usuarios, "descricao", Nivel.BAIXO, Status.ATRASADA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Tipo.INDIVIDUAL);
        TarefaDTO tarefaDTO = new TarefaDTO(Collections.singletonList(new UsuarioDTO(1L, "@gmail.com")), "descricao", Nivel.BAIXO, Status.ATRASADA, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(2));

        when(tarefaRepository.buscaTarefasAtrasadas()).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.buscaTarefasAtrasadas();

        assertNotNull(resultado);
        assertEquals("@gmail.com", resultado.get(0).getUser().get(0).getEmail());
        assertEquals("descricao", resultado.get(0).getDescricao());
        verify(tarefaRepository).buscaTarefasAtrasadas();
        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas atrasadas")
    void buscaTarefasAtrasadasCase2() {

        when(tarefaRepository.buscaTarefasAtrasadas()).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class, () -> tarefaService.buscaTarefasAtrasadas());

        assertEquals("Nenhuma tarefa encotrada", exception.getMessage());
    }


    @Test
    @DisplayName("Deve retornar tarefas atrasadas associadas ao usuário logado")
    void buscaTarefasAtrasadasPorIdUsuarioCase1() throws GerenciamentoDeTarefasException {

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setStatus(Status.ATRASADA);
        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ATRASADA);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasAtrasadasPorIdUsuario(2L)).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class)))
                .thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.buscaTarefasAtrasadasPorIdUsuario();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        verify(tarefaRepository, times(1)).buscaTarefasAtrasadasPorIdUsuario(2L);
        verify(modelMapper, times(1)).map(any(Tarefa.class), eq(TarefaDTO.class));
        assertEquals(Status.ATRASADA, resultado.get(0).getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas atrasadas para o usuário logado")
    void buscaTarefasAtrasadasPorIdUsuarioCase2() throws GerenciamentoDeTarefasException {

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasAtrasadasPorIdUsuario(2L)).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                () -> tarefaService.buscaTarefasAtrasadasPorIdUsuario());

        assertEquals("Nenhuma tarefa encotrada", exception.getMessage());
        verify(tarefaRepository).buscaTarefasAtrasadasPorIdUsuario(eq(2L));
    }


    @Test
    @DisplayName("Deve retornar a lista de tarefas abertas corretamente")
    void buscarTarefasAbertasCase1() throws GerenciamentoDeTarefasException {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "@gmail.com");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        Tarefa tarefa = new Tarefa(1L, usuarios, "descricao", Nivel.BAIXO, Status.ABERTA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Tipo.INDIVIDUAL);
        TarefaDTO tarefaDTO = new TarefaDTO(Collections.singletonList(new UsuarioDTO(1L, "@gmail.com")), "descricao", Nivel.BAIXO, Status.ABERTA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(4));

        when(tarefaRepository.buscaTarefasAbertas()).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.buscarTarefasAbertas();

        assertNotNull(resultado);
        assertEquals("@gmail.com", resultado.get(0).getUser().get(0).getEmail());
        assertEquals("descricao", resultado.get(0).getDescricao());
        assertEquals(Status.ABERTA, resultado.get(0).getStatus());
        verify(tarefaRepository).buscaTarefasAbertas();
        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));


    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas abertas")
    void buscarTarefasAbertasCase2() throws GerenciamentoDeTarefasException {

        when(tarefaRepository.buscaTarefasAbertas()).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class, () -> tarefaService.buscarTarefasAbertas());

        assertEquals("Nenhuma tarefa encotrada", exception.getMessage());
        verify(tarefaRepository).buscaTarefasAbertas();
    }

    @Test
    @DisplayName("Deve retornar a lista de tarefas abertas para o usuário logado corretamente")
    void buscarTarefasAbertasPorIdUsuarioCase1() throws GerenciamentoDeTarefasException {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.ABERTA);

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setStatus(Status.ABERTA);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasAbertasPorIdUsuario(2L)).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);


        List<TarefaDTO> resultado = tarefaService.buscarTarefasAbertasPorIdUsuario();

        assertNotNull(resultado);
        assertEquals(Status.ABERTA, resultado.get(0).getStatus());
        verify(tarefaRepository).buscaTarefasAbertasPorIdUsuario(eq(2L));
        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));

    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas abertas para o usuário logado")
    void buscarTarefasAbertasPorIdUsuarioCase2() throws GerenciamentoDeTarefasException {

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasAbertasPorIdUsuario(2L)).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                () -> tarefaService.buscarTarefasAbertasPorIdUsuario());


        assertEquals("Nenhuma tarefa encotrada", exception.getMessage());
        verify(tarefaRepository).buscaTarefasAbertasPorIdUsuario(eq(2L));
    }

    @Test
    @DisplayName("Deve retornar a lista de tarefas realizadas corretamente")
    void buscaTarefasRealizadasCase1() throws GerenciamentoDeTarefasException {

        Usuario usuario = new Usuario(Role.ROLE_USER, "senha", "@gmail.com");
        List<Usuario> usuarios = Collections.singletonList(usuario);

        Tarefa tarefa = new Tarefa(1L, usuarios, "descricao", Nivel.BAIXO, Status.REALIZADA, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), Tipo.INDIVIDUAL);
        TarefaDTO tarefaDTO = new TarefaDTO(Collections.singletonList(new UsuarioDTO(1L, "@gmail.com")), "descricao", Nivel.BAIXO, Status.REALIZADA, LocalDateTime.now().plusDays(1), LocalDateTime.now().minusDays(2));

        when(tarefaRepository.buscaTarefasRealizadas()).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.buscaTarefasRealizadas();

        assertNotNull(resultado);
        assertEquals(Status.REALIZADA, resultado.get(0).getStatus());
        assertEquals("@gmail.com", resultado.get(0).getUser().get(0).getEmail());
        assertEquals("descricao", resultado.get(0).getDescricao());
        verify(tarefaRepository).buscaTarefasRealizadas();
        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));


    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas realizadas")
    void buscaTarefasRealizadasCase2() {

        when(tarefaRepository.buscaTarefasRealizadas()).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class, () -> tarefaService.buscaTarefasRealizadas());

        assertEquals("Nenhuma Tarefa concluída", exception.getMessage());
        verify(tarefaRepository).buscaTarefasRealizadas();
    }


    @Test
    @DisplayName("Deve retornar a lista de tarefas realizadas para o usuário logado corretamente")
    void buscaTarefasRealizadasPorIdUsuarioCase1() throws GerenciamentoDeTarefasException {

        Tarefa tarefa = criarTarefa();
        tarefa.setStatus(Status.REALIZADA);

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setStatus(Status.REALIZADA);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasRealizadasPorIdUsuario(2L)).thenReturn(Collections.singletonList(tarefa));
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class))).thenReturn(tarefaDTO);


        List<TarefaDTO> resultado = tarefaService.buscaTarefasRealizadasPorIdUsuario();

        assertNotNull(resultado);
        assertEquals(Status.REALIZADA, resultado.get(0).getStatus());
        verify(tarefaRepository).buscaTarefasRealizadasPorIdUsuario(eq(2L));
        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas realizadas para o usuário logado")
    void buscaTarefasRealizadasPorIdUsuarioCase2() {

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscaTarefasRealizadasPorIdUsuario(2L)).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                () -> tarefaService.buscaTarefasRealizadasPorIdUsuario());


        assertEquals("Nenhuma Tarefa concluída", exception.getMessage());
        verify(tarefaRepository).buscaTarefasRealizadasPorIdUsuario(eq(2L));
    }

    @Test
    @DisplayName("Deve buscar tarefas entre duas datas corretamente")
    void buscarTarefasEntreAsDatasCase1() throws GerenciamentoDeTarefasException {

        LocalDate date1 = LocalDate.of(2025, 9, 10);
        LocalDate date2 = LocalDate.of(2025, 9, 20);

        LocalDateTime dataInicio = date1.atStartOfDay();
        LocalDateTime dataDeTermino = date2.atTime(LocalTime.MAX);


        Tarefa tarefa = new Tarefa();
        tarefa.setDataDaTarefa(dataInicio);
        tarefa.setPrazoDaTarefa(dataDeTermino);

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setDataDaTarefa(dataInicio);
        tarefaDTO.setPrazoDaTarefa(dataDeTermino);

        when(tarefaRepository.buscarTarefasEntreAsDatas(dataInicio, dataDeTermino)).thenReturn(Collections.singletonList(tarefa));

        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class)))
                .thenReturn(tarefaDTO);

        List<TarefaDTO> resultado = tarefaService.buscarTarefasEntreAsDatas(date1, date2);

        assertNotNull(resultado);
        assertEquals(dataInicio, resultado.get(0).getDataDaTarefa());
        assertEquals(dataDeTermino, resultado.get(0).getPrazoDaTarefa());

        verify(tarefaRepository).buscarTarefasEntreAsDatas(eq(dataInicio),eq(dataDeTermino));

        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas entre as datas")
    void buscarTarefasEntreAsDatasCase2() {

        LocalDate date1 = LocalDate.of(2025, 9, 10);
        LocalDate date2 = LocalDate.of(2025, 9, 20);

        LocalDateTime dataInicio = date1.atStartOfDay();
        LocalDateTime dataDeTermino = date2.atTime(LocalTime.MAX);

        when(tarefaRepository.buscarTarefasEntreAsDatas(dataInicio, dataDeTermino)).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                ()-> tarefaService.buscarTarefasEntreAsDatas(date1,date2));

        assertEquals("Nenhuma tarefa entre essas datas",exception.getMessage());
        verify(tarefaRepository).buscarTarefasEntreAsDatas(eq(dataInicio),eq(dataDeTermino));
    }

    @Test
    @DisplayName("Deve buscar tarefas corretamente entre duas datas para um usuário específico")
    void buscarTarefasEntreAsDatasPorIdUsuarioCase1() throws GerenciamentoDeTarefasException {

        LocalDate date1 = LocalDate.of(2025, 9, 10);
        LocalDate date2 = LocalDate.of(2025, 9, 20);

        LocalDateTime dataInicio = date1.atStartOfDay();
        LocalDateTime dataDeTermino = date2.atTime(LocalTime.MAX);

        Tarefa tarefa = new Tarefa();
        tarefa.setDataDaTarefa(dataInicio);
        tarefa.setPrazoDaTarefa(dataDeTermino);

        TarefaDTO tarefaDTO = new TarefaDTO();
        tarefaDTO.setDataDaTarefa(dataInicio);
        tarefaDTO.setPrazoDaTarefa(dataDeTermino);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(modelMapper.map(any(Tarefa.class), eq(TarefaDTO.class)))
                .thenReturn(tarefaDTO);
        when(tarefaRepository.buscarTarefasEntreAsDatasPorIdUsuario(dataInicio,dataDeTermino,2L)).thenReturn(Collections.singletonList(tarefa));

        List<TarefaDTO> resultado = tarefaService.buscarTarefasEntreAsDatasPorIdUsuario(date1,date2);

        assertNotNull(resultado);
        assertEquals(dataInicio, resultado.get(0).getDataDaTarefa());
        assertEquals(dataDeTermino, resultado.get(0).getPrazoDaTarefa());

        verify(tarefaRepository).buscarTarefasEntreAsDatasPorIdUsuario(eq(dataInicio),eq(dataDeTermino),eq(2L));

        verify(modelMapper).map(any(Tarefa.class), eq(TarefaDTO.class));

    }

    @Test
    @DisplayName("Deve lançar exceção quando não houver tarefas no período para o usuário")
    void buscarTarefasEntreAsDatasPorIdUsuarioCase2() {


        LocalDate date1 = LocalDate.of(2025, 9, 10);
        LocalDate date2 = LocalDate.of(2025, 9, 20);

        LocalDateTime dataInicio = date1.atStartOfDay();
        LocalDateTime dataDeTermino = date2.atTime(LocalTime.MAX);

        when(usuarioService.obtemIdUsuario()).thenReturn(2L);
        when(tarefaRepository.buscarTarefasEntreAsDatasPorIdUsuario(dataInicio, dataDeTermino,2L)).thenReturn(Collections.emptyList());

        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                ()-> tarefaService.buscarTarefasEntreAsDatasPorIdUsuario(date1,date2));

        assertEquals("Nenhuma tarefa entre essas datas",exception.getMessage());
        verify(tarefaRepository).buscarTarefasEntreAsDatasPorIdUsuario(eq(dataInicio),eq(dataDeTermino),eq(2L));


    }

    @Test
    @DisplayName("Deve concluir uma tarefa com sucesso")
    void concluiTarefaCase1() throws GerenciamentoDeTarefasException {

        Tarefa tarefa = criarTarefa();
        tarefa.setId(2L);

        when(tarefaRepository.findById(tarefa.getId())).thenReturn(Optional.of(tarefa));
        tarefa.setStatus(Status.REALIZADA);

        String resultado = tarefaService.concluiTarefa(2L);

        assertEquals("Tarefa concluída",resultado);
        verify(tarefaRepository).saveAndFlush(tarefa);

    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar concluir uma tarefa inexistente")
    void concluiTarefaCase2() throws GerenciamentoDeTarefasException {

        when(tarefaRepository.findById(2L)).thenReturn(Optional.empty());


        GerenciamentoDeTarefasException exception = assertThrows(GerenciamentoDeTarefasException.class,
                ()-> tarefaService.concluiTarefa(2L));

        assertEquals("Tarefa não encontrada",exception.getMessage());
        verify(tarefaRepository,never()).saveAndFlush(any(Tarefa.class));
    }

    @Test
    void concluiTarefaPorIdUsuario() {
    }

    @Test
    void editaTarefa() {
    }

    @Test
    void deleteTarefa() {
    }

    @Test
    void deleteTarefaPorIdUsuario() {
    }
}