package com.algaworks.algamoney.api.repository;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.pessoa.PessoaRepositoryQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

    List<Pessoa> findByAtivo(boolean ativo);
}
