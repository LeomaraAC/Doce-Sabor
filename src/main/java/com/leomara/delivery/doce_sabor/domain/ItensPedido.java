package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ItensPedidoPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class ItensPedido {

    @EmbeddedId
    private ItensPedidoPK id;
    private Integer qtde;
    private Double valor;

    public ItensPedido() {}

    public ItensPedido(Produto produto, Pedido pedido, Integer qtde, Double valor) {
        this.id.setProduto(produto);
        this.id.setPedido(pedido);
        this.qtde = qtde;
        this.valor = valor;
    }

    public Produto getProduto() {
        return id.getProduto();
    }

    public Pedido getPedido() {
        return id.getPedido();
    }

    public ItensPedidoPK getId() {
        return id;
    }

    public void setId(ItensPedidoPK id) {
        this.id = id;
    }

    public Integer getQtde() {
        return qtde;
    }

    public void setQtde(Integer qtde) {
        this.qtde = qtde;
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
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
