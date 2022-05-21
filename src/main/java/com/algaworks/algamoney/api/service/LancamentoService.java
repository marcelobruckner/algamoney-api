package com.algaworks.algamoney.api.service;

import java.util.List;

import com.algaworks.algamoney.api.exceptionhandler.CategoriaNaoEncontradaException;
import com.algaworks.algamoney.api.exceptionhandler.LancamentoNaoEncontradaException;
import com.algaworks.algamoney.api.exceptionhandler.PessoaNaoEncontradaException;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.repository.projection.ResumoLancamento;
import com.algaworks.algamoney.api.service.exception.LancamentoBusinessExcepton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LancamentoService {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PessoaService pessoaService;

    public Lancamento salvar(Lancamento lancamento) {
        try {
            categoriaService.buscarPorId(lancamento.getCategoria().getCodigo());
        } catch (CategoriaNaoEncontradaException ex) {
            throw new LancamentoBusinessExcepton();
        }

        Pessoa pessoa;
        try {
            pessoa = pessoaService.buscar(lancamento.getPessoa().getCodigo());
        } catch (PessoaNaoEncontradaException ex) {
            throw new LancamentoBusinessExcepton();
        }

        if (pessoa.isInativo()) {
            throw new LancamentoBusinessExcepton();
        }

        lancamento = lancamentoRepository.save(lancamento);
        return lancamento;
    }

    public List<Lancamento> buscar() {
        return lancamentoRepository.findAll();
    }

    public Lancamento buscar(Long id) {
        return lancamentoRepository.findById(id).orElseThrow(
                () -> new LancamentoNaoEncontradaException(id));
    }

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.filtrar(lancamentoFilter, pageable);
    }

    public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        return lancamentoRepository.resumir(lancamentoFilter, pageable);
    }

    public void delete(Long id) {
        lancamentoRepository.deleteById(id);
    }
}
