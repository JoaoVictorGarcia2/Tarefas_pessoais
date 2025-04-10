package com.joao.tarefas.service;

import java.util.List;

import com.joao.tarefas.model.entity.Tarefa;
import com.joao.tarefas.model.enums.StatusLancamento;

public interface LancamentoService {
	
	Tarefa salvar(Tarefa lancamento);
	
	Tarefa atualizar(Tarefa lancamento);
	
	void deletar(Tarefa lancamento);
	
	List<Tarefa> buscar(Tarefa lancamentoFiltro);
	
	void atualizarStatus(Tarefa lancamento, StatusLancamento status);
	
}
