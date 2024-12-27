package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaCompatilhadaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaUpdateDTO;


import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.TarefaService;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;


import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/v1/tarefa")
@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:4200")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioService usuarioService;


    @Secured("ROLE_USER")
    @PostMapping("/criaTarefa")
    public ResponseEntity<TarefaDTO> criaTarefa(@RequestBody TarefaDTO tarefaDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaDTO>(tarefaService.criaTarefa(tarefaDTO), HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @PostMapping("/criaTarefaCompatilhada")
    public ResponseEntity<TarefaCompatilhadaDTO> criaTarefaCompatilhada(@RequestBody TarefaCompatilhadaDTO tarefaCompatilhadaDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaCompatilhadaDTO>(tarefaService.criaTarefaCompatilhada(tarefaCompatilhadaDTO), HttpStatus.CREATED);
    }


    //funçoes admin


//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/buscaTarefas")
    public ResponseEntity<List<TarefaDTO>> buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefas(), HttpStatus.OK);
    }


    @Secured("ROLE_ADMIN")
    @GetMapping("/buscarTarefasAtrasdas")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasAtrasadas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/buscarTarefasAbertas")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasAbertas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/buscarTarefasRealizadas")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasRealizadas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasRealizadas(), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/buscarTarefasEntreAsDatas/{dataDeInicio}/{dataDeTermino}")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasEntreAsDatas(@PathVariable LocalDate dataDeInicio, @PathVariable LocalDate dataDeTermino) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasEntreAsDatas(dataDeInicio, dataDeTermino), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/editaTarefa")
    public ResponseEntity<TarefaUpdateDTO> editaTarefa(@RequestBody TarefaUpdateDTO tarefaUpdateDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaUpdateDTO>(tarefaService.editaTarefa(tarefaUpdateDTO), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/deleteTarefa/{idTarefa}")
    public ResponseEntity<Void> deleteTarefaById(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {
        tarefaService.deleteTarefa(idTarefa);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/concluirTarefa/{idTarefa}")
    public ResponseEntity<String> concluiTarefa(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<String>(tarefaService.concluiTarefa(idTarefa), HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/buscaTarefasAbertas")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasAbertas() throws GerenciamentoDeTarefasException {

        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasAbertas(), HttpStatus.OK);
    }

    /*PORUSUARIO //Acesso user*


     */

    @Secured("ROLE_USER")
    @GetMapping("/buscaTarefasPorIdUsuario")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasPorIdUsuario() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasPorIdUsuario(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/buscaTarefasAtrasadasPorIdUsuario")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasAtrasadasPorIdUsuario() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadasPorIdUsuario(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/buscarTarefasAbertasPorIdUsuario")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasAbertasPorIdUsuario() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasAbertasPorIdUsuario(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/buscaTarefasRealizadasPorIdUsuario")
    public ResponseEntity<List<TarefaDTO>> buscaTarefasRealizadasPorIdUsuario() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasRealizadasPorIdUsuario(), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @GetMapping("/buscarTarefasEntreAsDatasPorIdUsuario/{dataDeInicio}/{dataDeTermino}")
    public ResponseEntity<List<TarefaDTO>> buscarTarefasEntreAsDatasPorIdUsuario(@PathVariable LocalDate dataDeInicio, @PathVariable LocalDate dataDeTermino) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasEntreAsDatasPorIdUsuario(dataDeInicio, dataDeTermino), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @PutMapping("/concluiTarefaPorIdUsuario/{idTarefa}")
    public ResponseEntity<String> concluiTarefaPorIdUsuario(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<String>(tarefaService.concluiTarefaPorIdUsuario(idTarefa), HttpStatus.OK);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/deleteTarefaPorIdUsuario/{idTarefa}")
    public ResponseEntity<Void> deleteTarefaByIdUsuario(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {
        tarefaService.deleteTarefaPorIdUsuario(idTarefa);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
