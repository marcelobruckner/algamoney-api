package com.algaworks.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "categoria")
public class Categoria {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long codigo;

  @NotNull
  @Size(max = 50)
  private String nome;

  public Categoria() {
  }


  public Long getCodigo() {
    return this.codigo;
  }

  public @NotNull @Size(max = 50) String getNome() {
    return this.nome;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public void setNome(@NotNull @Size(max = 50) String nome) {
    this.nome = nome;
  }

  protected boolean canEqual(final Object other) {
    return other instanceof Categoria;
  }

  public String toString() {
    return "Categoria(codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Categoria categoria = (Categoria) o;
    return codigo.equals(categoria.codigo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo);
  }
}
