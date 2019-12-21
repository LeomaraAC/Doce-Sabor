package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ItensPedidoPK;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "Chave composta com o Produto e o Pedido")
    @EmbeddedId
    private ItensPedidoPK id;

    @ApiModelProperty(value = "Quantidede de itens do produto")
    private Integer qtde;

    @ApiModelProperty(value = "Preço unitário do item")
    private Double valor;


    public ItensPedido(Produto produto, Pedido pedido, Integer qtde, Double valor) {
        this.id.setProduto(produto);
        this.id.setPedido(pedido);
        this.qtde = qtde;
        this.valor = valor;
    }

    public void setPedido(Pedido pedido) {
        this.id.setPedido(pedido);
    }


    public void setProduto(Produto produto) {
        this.id.setProduto(produto);
    }

    public Pedido getPedido () {
        return this.id.getPedido();
    }

    public Produto getProduto() {
        return this.id.getProduto();
    }

}
