package com.algaworks.algamoney.api.exceptionhandler;

public class LancamentoNaoEncontradaException extends EntidadeNaoEncontradaException{
    public LancamentoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public LancamentoNaoEncontradaException(Long id) {
        this(String.format("Não existe um cadastro de lancamento com código %d", id));
    }
}
