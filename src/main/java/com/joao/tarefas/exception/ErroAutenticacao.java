package com.joao.tarefas.exception;

public class ErroAutenticacao extends RuntimeException {
	
	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}

}
