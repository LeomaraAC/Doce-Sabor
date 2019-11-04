package com.leomara.delivery.doce_sabor.domain.PK;

import com.leomara.delivery.doce_sabor.domain.Pedido;
import com.leomara.delivery.doce_sabor.domain.Produto;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItensPedidoPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItensPedidoPK that = (ItensPedidoPK) o;
        return produto.equals(that.produto) &&
                pedido.equals(that.pedido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto, pedido);
    }
}
