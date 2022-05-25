package com.algaworks.algamoney.api.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.filter.PessoaFilter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

  @PersistenceContext
  private EntityManager manager;

  @Override
  public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
    Root<Pessoa> root = criteria.from(Pessoa.class);

    Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
    criteria.where(predicates);

    TypedQuery<Pessoa> query = manager.createQuery(criteria);

    adicionarRestricoesDePaginacao(query, pageable);

    return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));
  }

  private Long total(PessoaFilter pessoaFilter) {
    CriteriaBuilder builder = manager.getCriteriaBuilder();
    CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

    Root<Pessoa> root = criteria.from(Pessoa.class);

    Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
    criteria.where(predicates);

    criteria.select(builder.count(root));
    return manager.createQuery(criteria).getSingleResult();
  }

  private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
    int paginaAtual = pageable.getPageNumber();
    int totalRegistrosPorPagina = pageable.getPageSize();
    int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

    query.setFirstResult(primeiroRegistroDaPagina);
    query.setMaxResults(totalRegistrosPorPagina);
  }

  private Predicate[] criarRestricoes(PessoaFilter pessoaFilter,
      CriteriaBuilder builder,
      Root<Pessoa> root) {

    List<Predicate> predicates = new ArrayList<>();

    if (StringUtils.hasLength(pessoaFilter.getNome())) {
      predicates.add(
          builder.like(
              builder.lower(root.get("nome")), "%" + pessoaFilter.getNome().toLowerCase() + "%"));
    }

    if (StringUtils.hasLength(pessoaFilter.getCidade())) {
      predicates.add(
          builder.like(
              builder.lower(root.get("endereco").get("cidade")), "%" + pessoaFilter.getCidade().toLowerCase() + "%"));
    }

    if (StringUtils.hasLength(pessoaFilter.getEstado())) {
      predicates.add(
          builder.like(
              builder.lower(root.get("endereco").get("estado")), "%" + pessoaFilter.getEstado().toLowerCase() + "%"));
    }

    return predicates.toArray(new Predicate[0]);
  }

}
