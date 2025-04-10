package com.joao.tarefas.service.impl;

import org.springframework.stereotype.Service;

import com.joao.tarefas.model.entity.Tarefa;
import com.joao.tarefas.model.repository.TarefaRepository;
import com.joao.tarefas.service.TarefaService;

import java.util.List;

@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepository repository;

    public TarefaServiceImpl(TarefaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        return repository.save(tarefa);
    }

    @Override
    public Tarefa atualizar(Tarefa tarefa) {
        if (repository.existsById(tarefa.getId())) {
            return repository.save(tarefa);
        }
        throw new RuntimeException("Tarefa não encontrada");
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    @Override
    public Tarefa obterPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }
}