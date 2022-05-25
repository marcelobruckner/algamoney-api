package com.algaworks.algamoney.api.repository.pessoa;

import java.util.List;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PessoaRepositoryQuery {

  Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
}
