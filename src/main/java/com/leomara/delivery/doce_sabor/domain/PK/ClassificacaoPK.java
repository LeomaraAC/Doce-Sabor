package com.leomara.delivery.doce_sabor.domain.PK;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.domain.Produto;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClassificacaoPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassificacaoPK that = (ClassificacaoPK) o;
        return cliente.equals(that.cliente) &&
                produto.equals(that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, produto);
    }
}
