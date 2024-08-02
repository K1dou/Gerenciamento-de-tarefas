package com.kidou.aplicativo.de.gerenciamento.de.tarefas.service;

import com.kidou.aplicativo.de.gerenciamento.de.tarefas.exception.GerenciamentoDeTarefasException;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaCompatilhadaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.dtos.tarefaDTO.TarefaUpdateDTO;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Tarefa;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.entity.Usuario;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.model.enums.Status;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.TarefaRepository;
import com.kidou.aplicativo.de.gerenciamento.de.tarefas.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TarefaDTO criaTarefa(TarefaDTO tarefaDTO) throws GerenciamentoDeTarefasException {

        if (tarefaDTO.getUser().size() > 1) {
            throw new GerenciamentoDeTarefasException("informe apenas um Usuario");
        }

        LocalDateTime agora = LocalDateTime.now();
        if (tarefaDTO.getDataDaTarefa().isBefore(agora)) {
            throw new GerenciamentoDeTarefasException("Não é possivel criar uma tarefa para um dia anterior a hoje!");
        }

        Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);
        tarefaRepository.save(tarefa);


        return modelMapper.map(tarefa, TarefaDTO.class);
    }

    public TarefaCompatilhadaDTO criaTarefaCompatilhada(TarefaCompatilhadaDTO tarefaCompatilhadaDTO) throws GerenciamentoDeTarefasException {

        LocalDateTime agora = LocalDateTime.now();
        if (tarefaCompatilhadaDTO.getDataDaTarefa().isBefore(agora)) {
            throw new GerenciamentoDeTarefasException("Não é possivel criar uma tarefa para um dia anterior a hoje!");
        }
        Tarefa tarefa = modelMapper.map(tarefaCompatilhadaDTO, Tarefa.class);
        tarefaRepository.save(tarefa);

        return modelMapper.map(tarefa, TarefaCompatilhadaDTO.class);

    }


    public List<TarefaDTO> buscaTarefas() throws GerenciamentoDeTarefasException, ParseException {
        List<Tarefa> tarefas = tarefaRepository.findAll();
        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

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

    public List<TarefaDTO> buscaTarefasPorIdUsuario(Long idUsuario) throws GerenciamentoDeTarefasException, ParseException {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasPorIdUsuario(idUsuario);

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

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


    public List<TarefaDTO> buscaTarefasAtrasadas() throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAtrasadas();

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }
        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaDTOS;

    }

    public List<TarefaDTO> buscaTarefasAtrasadasPorIdUsuario(Long idUsuario) throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAtrasadasPorIdUsuario(idUsuario);

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }

        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaDTOS;

    }


    public List<TarefaDTO> buscarTarefasAbertas() throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAbertas();

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }
        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());

        return tarefaDTOS;
    }


    public List<TarefaDTO> buscarTarefasAbertasPorIdUsuario(Long idUsuario) throws GerenciamentoDeTarefasException {

        List<Tarefa> tarefas = tarefaRepository.buscaTarefasAbertasPorIdUsuario(idUsuario);

        if (tarefas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa encotrada");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }
        List<TarefaDTO> tarefaDTOS = tarefas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());

        return tarefaDTOS;

    }

    public List<TarefaDTO> buscaTarefasRealizadas() throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefasRealizadas = tarefaRepository.buscaTarefasRealizadas();
        if (tarefasRealizadas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma Tarefa concluída");
        }
        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefasRealizadas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }

        List<TarefaDTO> tarefaRealizadasDTOS = tarefasRealizadas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaRealizadasDTOS;
    }

    public List<TarefaDTO> buscaTarefasRealizadasPorIdUsuario(Long idUsuario) throws GerenciamentoDeTarefasException {
        List<Tarefa> tarefasRealizadas = tarefaRepository.buscaTarefasRealizadasPorIdUsuario(idUsuario);
        if (tarefasRealizadas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma Tarefa concluída");
        }
        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefasRealizadas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }

        List<TarefaDTO> tarefaRealizadasDTOS = tarefasRealizadas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefaRealizadasDTOS;
    }


    public List<TarefaDTO> buscarTarefasEntreAsDatas(LocalDate dataDeInicio, LocalDate dataDeTermino) throws GerenciamentoDeTarefasException {

        LocalDateTime inicioDoDia = dataDeInicio.atStartOfDay();
        LocalDateTime fimDoDia = dataDeTermino.atTime(LocalTime.MAX);

        List<Tarefa> tarefasEntreDatas = tarefaRepository.buscarTarefasEntreAsDatas(inicioDoDia, fimDoDia);

        if (tarefasEntreDatas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa entre essas datas");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefasEntreDatas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }
        List<TarefaDTO> tarefasEntreDatasDTO = tarefasEntreDatas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefasEntreDatasDTO;
    }

    public List<TarefaDTO> buscarTarefasEntreAsDatasPorIdUsuario(LocalDate dataDeInicio, LocalDate dataDeTermino, Long idUsuario) throws GerenciamentoDeTarefasException {

        LocalDateTime inicioDoDia = dataDeInicio.atStartOfDay();
        LocalDateTime fimDoDia = dataDeTermino.atTime(LocalTime.MAX);

        List<Tarefa> tarefasEntreDatas = tarefaRepository.buscarTarefasEntreAsDatasPorIdUsuario(inicioDoDia, fimDoDia, idUsuario);

        if (tarefasEntreDatas.isEmpty()) {
            throw new GerenciamentoDeTarefasException("Nenhuma tarefa entre essas datas");
        }

        LocalDateTime agora = LocalDateTime.now();
        for (Tarefa t : tarefasEntreDatas) {
            if (t.getPrazoDaTarefa().isBefore(agora)) {
                t.setStatus(Status.ATRASADA);
                tarefaRepository.saveAndFlush(t);
            }
        }
        List<TarefaDTO> tarefasEntreDatasDTO = tarefasEntreDatas.stream().map(item -> modelMapper.map(item, TarefaDTO.class)).collect(Collectors.toList());
        return tarefasEntreDatasDTO;
    }


    public String concluiTarefa(Long idTarefa) throws GerenciamentoDeTarefasException {
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        tarefa.setStatus(Status.REALIZADA);
        tarefaRepository.saveAndFlush(tarefa);
        return "Tarefa concluída";
    }

    public String concluiTarefaPorIdUsuario(Long idTarefa, Long idUsuario) throws GerenciamentoDeTarefasException {
        tarefaRepository.concluiTarefaPorIdUsuario(idTarefa, idUsuario);

        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        if (tarefa.getStatus() != Status.REALIZADA) {
            throw new GerenciamentoDeTarefasException("Falha ao concluir tarefa");
        }

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

    public void deleteTarefaPorIdUsuario(Long idTarefa, Long idUsuario) throws GerenciamentoDeTarefasException {
        Tarefa tarefa = tarefaRepository.findById(idTarefa).orElseThrow(() -> new GerenciamentoDeTarefasException("Tarefa não encontrada"));
        boolean isTheUser = tarefaRepository.tarefaIsTheUser(idTarefa, idUsuario);
        if (isTheUser) {
            tarefaRepository.deleteById(idTarefa);
        }else throw new GerenciamentoDeTarefasException("O usuario não tem essa tarefa");

    }


}
