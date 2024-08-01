package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.CriaTarefaDTO;
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

    //n pode criar tarefa pra dia anterior, settar tarefas criadas para em Aberta, quando buscar a tarefa atualizar o nivel dela

    @PostMapping("/criaTarefa")
    public ResponseEntity<CriaTarefaDTO>criaTarefa(@RequestBody CriaTarefaDTO criaTarefaDTO) throws GerenciamentoDeTarefasException {


        return new ResponseEntity<CriaTarefaDTO>(tarefaService.criaTarefa(criaTarefaDTO), HttpStatus.CREATED);
    }

    @GetMapping("/buscaTarefas")
    public ResponseEntity<List<CriaTarefaDTO>>buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {


        return new ResponseEntity<List<CriaTarefaDTO>>(tarefaService.buscaTarefas(),HttpStatus.OK);
    }

}
