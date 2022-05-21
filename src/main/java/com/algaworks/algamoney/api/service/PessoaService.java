package com.algaworks.algamoney.api.service;

import java.util.List;

import com.algaworks.algamoney.api.exceptionhandler.PessoaNaoEncontradaException;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> listar() {
        return repository.findAll();
    }

    public List<Pessoa> listarAtivos() {
        return repository.findByAtivo(true);
    }

    public List<Pessoa> listarInativos() {
        return repository.findByAtivo(false);
    }

    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    public Pessoa buscar(Long codigo) {
        return repository.findById(codigo).orElseThrow(
                () -> new PessoaNaoEncontradaException(codigo));
    }

    public void remover(Long codigo) {
        repository.deleteById(codigo);
    }

    public Pessoa atualizar(Long codigo, Pessoa pessoa) {
        Pessoa pessoaSalva = this.buscar(codigo);
        BeanUtils.copyProperties(pessoa, pessoaSalva, "id");
        return this.salvar(pessoaSalva);
    }

    public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoa = this.buscar(codigo);
        pessoa.setAtivo(ativo);
        this.salvar(pessoa);
    }

}
