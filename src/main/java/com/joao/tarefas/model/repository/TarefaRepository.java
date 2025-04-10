package com.joao.tarefas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joao.tarefas.model.entity.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
}