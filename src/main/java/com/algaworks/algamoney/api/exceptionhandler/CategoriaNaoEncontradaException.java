package com.algaworks.algamoney.api.exceptionhandler;

public class CategoriaNaoEncontradaException extends EntidadeNaoEncontradaException{
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CategoriaNaoEncontradaException(Long categoriaId) {
        this(String.format("Não existe um cadastro de categoria com código %d", categoriaId));
    }
}
