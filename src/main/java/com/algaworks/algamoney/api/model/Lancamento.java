package com.algaworks.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lancamento")
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    private String descricao;

    @Column(name = "data_vencimento")
    @NotNull
    private LocalDate dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDate dataPagamento;

    @NotNull
    private BigDecimal valor;
    private String observacao;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoLancamento tipo;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria")
    @NotNull
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "codigo_pessoa")
    @NotNull
    private Pessoa pessoa;

    public Lancamento() {
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public @NotNull String getDescricao() {
        return this.descricao;
    }

    public @NotNull LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    public LocalDate getDataPagamento() {
        return this.dataPagamento;
    }

    public @NotNull BigDecimal getValor() {
        return this.valor;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public @NotNull TipoLancamento getTipo() {
        return this.tipo;
    }

    public @NotNull Categoria getCategoria() {
        return this.categoria;
    }

    public @NotNull Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public void setDescricao(@NotNull String descricao) {
        this.descricao = descricao;
    }

    public void setDataVencimento(@NotNull LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public void setDataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public void setValor(@NotNull BigDecimal valor) {
        this.valor = valor;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public void setTipo(@NotNull TipoLancamento tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(@NotNull Categoria categoria) {
        this.categoria = categoria;
    }

    public void setPessoa(@NotNull Pessoa pessoa) {
        this.pessoa = pessoa;
    }



    protected boolean canEqual(final Object other) {
        return other instanceof Lancamento;
    }



    public String toString() {
        return "Lancamento(codigo=" + this.getCodigo() + ", descricao=" + this.getDescricao() + ", dataVencimento=" + this.getDataVencimento() + ", dataPagamento=" + this.getDataPagamento() + ", valor=" + this.getValor() + ", observacao=" + this.getObservacao() + ", tipo=" + this.getTipo() + ", categoria=" + this.getCategoria() + ", pessoa=" + this.getPessoa() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lancamento that = (Lancamento) o;
        return codigo.equals(that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
