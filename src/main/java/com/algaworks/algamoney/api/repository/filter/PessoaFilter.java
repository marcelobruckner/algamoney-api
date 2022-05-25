package com.algaworks.algamoney.api.repository.filter;

import lombok.Data;

@Data
public class PessoaFilter {
  private String nome;
  private String cidade;
  private String estado;
}
