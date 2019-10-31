package com.leomara.delivery.doce_sabor.domain;

import java.util.Objects;

public class Classificacao {
    private Cliente cliente;
    private Produto produto;
    private Integer nota;
    private String obs;

    public Classificacao() {}

    public Classificacao(Cliente cliente, Produto produto, Integer nota, String obs) {
        this.cliente = cliente;
        this.produto = produto;
        this.nota = nota;
        this.obs = obs;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classificacao that = (Classificacao) o;
        return cliente.equals(that.cliente) &&
                produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, produto);
    }
}
