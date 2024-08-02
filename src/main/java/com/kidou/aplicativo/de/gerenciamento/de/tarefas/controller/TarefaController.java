package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaCompatilhadaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaUpdateDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Role;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/v1/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;


    @PostMapping("/criaTarefa")
    public ResponseEntity<TarefaDTO>criaTarefa(@RequestBody TarefaDTO tarefaDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaDTO>(tarefaService.criaTarefa(tarefaDTO), HttpStatus.CREATED);
    }

    @PostMapping("/criaTarefaCompatilhada")
    public ResponseEntity<TarefaCompatilhadaDTO>criaTarefaCompatilhada(@RequestBody TarefaCompatilhadaDTO tarefaCompatilhadaDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaCompatilhadaDTO>(tarefaService.criaTarefaCompatilhada(tarefaCompatilhadaDTO), HttpStatus.CREATED);
    }


    //funçoes admin
    @GetMapping("/buscaTarefas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasAtrasdas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasAtrasadas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasAbertas")
    public ResponseEntity<List<TarefaDTO>>buscarTarefasAbertas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasRealizadas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasRealizadas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasRealizadas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasEntreAsDatas/{dataDeInicio}/{dataDeTermino}")
    public ResponseEntity<List<TarefaDTO>>buscarTarefasEntreAsDatas(@PathVariable LocalDate dataDeInicio, @PathVariable LocalDate dataDeTermino) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasEntreAsDatas(dataDeInicio,dataDeTermino),HttpStatus.OK);
    }

    @PutMapping("/editaTarefa")
    public ResponseEntity<TarefaUpdateDTO>editaTarefa(@RequestBody TarefaUpdateDTO tarefaUpdateDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<TarefaUpdateDTO>(tarefaService.editaTarefa(tarefaUpdateDTO),HttpStatus.OK);
    }

    @DeleteMapping("/deleteTarefa/{idTarefa}")
    public ResponseEntity<Void>deleteTarefaById(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {
        tarefaService.deleteTarefa(idTarefa);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/concluirTarefa/{idTarefa}")
    public ResponseEntity<String>concluiTarefa(@PathVariable Long idTarefa) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<String>(tarefaService.concluiTarefa(idTarefa),HttpStatus.OK);
    }

    @GetMapping("/buscaTarefasAbertas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasAbertas() throws GerenciamentoDeTarefasException {

        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasAbertas(),HttpStatus.OK);
    }

                                                         /*PORUSUARIO //Acesso user*








                                                          */
    @GetMapping("/buscaTarefasPorIdUsuario/{idUsuario}")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasPorIdUsuario(@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasPorIdUsuario(idUsuario),HttpStatus.OK);
    }

    @GetMapping("/buscaTarefasAtrasadasPorIdUsuario/{idUsuario}")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasAtrasadasPorIdUsuario(@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadasPorIdUsuario(idUsuario),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/buscarTarefasAbertasPorIdUsuario/{idUsuario}")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasAbertasPorIdUsuario(@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasAbertasPorIdUsuario(idUsuario),HttpStatus.OK);
    }

    @GetMapping("/buscaTarefasRealizadasPorIdUsuario/{idUsuario}")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasRealizadasPorIdUsuario(@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasRealizadasPorIdUsuario(idUsuario),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasEntreAsDatasPorIdUsuario/{dataDeInicio}/{dataDeTermino}/{idUsuario}")
    public ResponseEntity<List<TarefaDTO>>buscarTarefasEntreAsDatasPorIdUsuario(@PathVariable LocalDate dataDeInicio, @PathVariable LocalDate dataDeTermino,@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscarTarefasEntreAsDatasPorIdUsuario(dataDeInicio,dataDeTermino,idUsuario),HttpStatus.OK);
    }

    @PutMapping("/concluiTarefaPorIdUsuario/{idTarefa}/{idUsuario}")
    public ResponseEntity<String>concluiTarefaPorIdUsuario(@PathVariable Long idTarefa,@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<String>(tarefaService.concluiTarefaPorIdUsuario(idTarefa,idUsuario),HttpStatus.OK);
    }

    @DeleteMapping("/deleteTarefaPorIdUsuario/{idTarefa}/{idUsuario}")
    public ResponseEntity<Void>deleteTarefaById(@PathVariable Long idTarefa,@PathVariable Long idUsuario) throws GerenciamentoDeTarefasException {
       tarefaService.deleteTarefaPorIdUsuario(idTarefa,idUsuario);

        return new ResponseEntity<>(HttpStatus.OK);
    }






}
