package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.CriaTarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.TarefaRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CriaTarefaDTO criaTarefa(CriaTarefaDTO tarefaDTO) throws GerenciamentoDeTarefasException {

       LocalDateTime agora= LocalDateTime.now();
       if (tarefaDTO.getDataDaTarefa().isBefore(agora)){
           throw new GerenciamentoDeTarefasException("Não é possivel criar uma tarefa para um dia anterior a hoje!");
       }

        Tarefa tarefa = modelMapper.map(tarefaDTO,Tarefa.class);
        tarefaRepository.save(tarefa);


        return modelMapper.map(tarefa, CriaTarefaDTO.class);
    }



    public List<CriaTarefaDTO>buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {
        List<Tarefa> tarefas = tarefaRepository.findAll();

        LocalDateTime agora = LocalDateTime.now();

        if (tarefas.isEmpty()){
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa no momento");
        }

        for (Tarefa t:tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)){
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }


        List<CriaTarefaDTO> tarefaDTOS = tarefas.stream().map(item->modelMapper.map(item, CriaTarefaDTO.class)).collect(Collectors.toList());
        return tarefaDTOS;
    }



}
