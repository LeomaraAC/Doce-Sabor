package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ItensPedidoPK;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ItensPedido {

    @EmbeddedId
    private ItensPedidoPK id;
    private Integer qtde;
    private Double valor;


    public ItensPedido(Produto produto, Pedido pedido, Integer qtde, Double valor) {
        this.id.setProduto(produto);
        this.id.setPedido(pedido);
        this.qtde = qtde;
        this.valor = valor;
    }

}
