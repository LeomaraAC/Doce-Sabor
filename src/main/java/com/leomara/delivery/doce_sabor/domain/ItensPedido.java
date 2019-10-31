package com.leomara.delivery.doce_sabor.domain;

import java.util.Objects;

public class ItensPedido {
    private Produto produto;
    private Pedido pedido;
    private Integer qtde;
    private Double desconto;
    private Double valor;

    public ItensPedido() {}

    public ItensPedido(Produto produto, Pedido pedido, Integer qtde, Double desconto, Double valor) {
        this.produto = produto;
        this.pedido = pedido;
        this.qtde = qtde;
        this.desconto = desconto;
        this.valor = valor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItensPedido that = (ItensPedido) o;
        return produto.equals(that.produto) &&
                pedido.equals(that.pedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, pedido);
    }
}
