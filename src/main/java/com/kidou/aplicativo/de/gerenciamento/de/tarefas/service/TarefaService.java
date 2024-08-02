package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaUpdateDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.TarefaRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public TarefaDTO criaTarefa(TarefaDTO tarefaDTO) throws GerenciamentoDeTarefasException {

        LocalDateTime agora = LocalDateTime.now();
        if (tarefaDTO.getDataDaTarefa().isBefore(agora)) {
            throw new GerenciamentoDeTarefasException("Não é possivel criar uma tarefa para um dia anterior a hoje!");
        }

        Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);
        tarefaRepository.save(tarefa);


        return modelMapper.map(tarefa, TarefaDTO.class);
    }


    public List<TarefaDTO> buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {
        List<Tarefa> tarefas = tarefaRepository.findAll();

        LocalDateTime agora = LocalDateTime.now();

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa no momento");
        }

        for (Tarefa t : tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }


        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaDTOS;
    }

    public List<TarefaDTO> buscaTarefasAtrasadas() {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAtrasadas();

        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaDTOS;

    }

    public List<TarefaDTO> buscarTarefasAbertas() {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAbertas();
        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());

        return tarefaDTOS;
    }

    public String concluiTarefa(Long idTarefa) throws GerenciamentoDeTarefasException {
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        tarefa.setStatus(Status.REALIZADA);
        tarefaRepository.saveAndFlush(tarefa);
        return "Tarefa concluída";
    }



    public TarefaUpdateDTO editaTarefa(TarefaUpdateDTO tarefaUpdateDTO) throws GerenciamentoDeTarefasException {

        Tarefa tarefa = tarefaRepository.findById(tarefaUpdateDTO.getId()).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        modelMapper.map(tarefaUpdateDTO, tarefa);
        tarefaRepository.save(tarefa);
        TarefaUpdateDTO tarefaEditada = modelMapper.map(tarefa, TarefaUpdateDTO.class);
        return tarefaEditada;
    }

    public void deleteTarefa(Long idTarefa) throws GerenciamentoDeTarefasException {
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        tarefaRepository.deleteById(idTarefa);
    }


    public List<TarefaDTO> buscaTarefasRealizadas() throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefasRealizadas = tarefaRepository.buscaTarefasRealizadas();
        if (tarefasRealizadas.isEmpty()){
            throw new GerenciamentoDeTarefasException("Nenhuma Tarefa concluída");
        }
        List<TarefaDTO>tarefaRealizadasDTOS = tarefasRealizadas.stream().map(item->modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaRealizadasDTOS;
    }
}
