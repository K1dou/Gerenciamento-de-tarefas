package com.kidou.aplicativo.de.gerenciamento.de.tarefas.controller;


import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.UsuarioDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/usuario")
@CrossOrigin(origins = "http://localhost:4200")

public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);



    @GetMapping("/users")
    public ResponseEntity<List<UsuarioDTO>>findAllUser() throws GerenciamentoDeTarefasException {


        return new ResponseEntity<List<UsuarioDTO>>(usuarioService.findAllUser(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void>deleteUserById(@PathVariable Long id) throws GerenciamentoDeTarefasException {
        usuarioService.deleteUserById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @GetMapping("/findUserByName/{name}")
    public ResponseEntity<List<UsuarioDTO>>findUserByName(@PathVariable String name){


        return new ResponseEntity<List<UsuarioDTO>>(usuarioService.findUserByName(name),HttpStatus.OK);
    }


}
