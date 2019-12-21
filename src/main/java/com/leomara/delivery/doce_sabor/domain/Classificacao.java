package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ClassificacaoPK;
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
public class Classificacao {

    @ApiModelProperty(value = "Chave Composta com Cliente e Produuto")
    @EmbeddedId
    private ClassificacaoPK id;

    @ApiModelProperty(value = "Nota da avaliação")
    private Integer nota;

    @ApiModelProperty(value = "Observação da avaliação")
    private String obs;

    public Classificacao(Cliente cliente, Produto produto, Integer nota, String obs) {
        this.id.setCliente(cliente);
        this.id.setProduto(produto);
        this.nota = nota;
        this.obs = obs;
    }

    public void setCliente(Cliente cliente) {
        this.id.setCliente(cliente);
    }

    public void setProduto(Produto produto) {
        this.id.setProduto(produto);
    }

    public Cliente getCliente() {
        return id.getCliente();
    }

    public Produto getProduto() {
        return id.getProduto();
    }
}
