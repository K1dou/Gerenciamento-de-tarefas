package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaUpdateDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

    @GetMapping("/buscaTarefas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasAtrasdas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasAtrasadas(){


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasAbertas")
    public ResponseEntity<List<TarefaDTO>>buscarTarefasAbertas(){


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasAtrasadas(),HttpStatus.OK);
    }

    @GetMapping("/buscarTarefasRealizadas")
    public ResponseEntity<List<TarefaDTO>>buscaTarefasRealizadas() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<TarefaDTO>>(tarefaService.buscaTarefasRealizadas(),HttpStatus.OK);
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

}
