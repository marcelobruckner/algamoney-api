package com.algaworks.algamoney.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    private String nome;

    private boolean ativo;

    @Embedded
    private Endereco endereco;

    public Pessoa() {
    }

    @JsonIgnore
    @Transient
    public boolean isInativo(){
        return !this.ativo;
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public @NotNull String getNome() {
        return this.nome;
    }

    public boolean isAtivo() {
        return this.ativo;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setNome(@NotNull String nome) {
        this.nome = nome;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Pessoa;
    }

    public String toString() {
        return "Pessoa(codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ", ativo=" + this.isAtivo() + ", endereco=" + this.getEndereco() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return codigo.equals(pessoa.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
