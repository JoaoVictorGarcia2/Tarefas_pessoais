package com.joao.tarefas.service;

import java.util.List;

import com.joao.tarefas.model.entity.Tarefa;

public interface TarefaService {
    Tarefa salvar(Tarefa tarefa);
    Tarefa atualizar(Tarefa tarefa);  
    void deletar(Long id);
    List<Tarefa> listar();
    Tarefa obterPorId(Long id);  
}