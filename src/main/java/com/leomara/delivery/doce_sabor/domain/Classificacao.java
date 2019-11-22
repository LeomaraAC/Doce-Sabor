package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ClassificacaoPK;
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

    @EmbeddedId
    private ClassificacaoPK id;
    private Integer nota;
    private String obs;

    public Classificacao(Cliente cliente, Produto produto, Integer nota, String obs) {
        this.id.setCliente(cliente);
        this.id.setProduto(produto);
        this.nota = nota;
        this.obs = obs;
    }

    public Cliente getCliente() {
        return id.getCliente();
    }

    public Produto getProduto() {
        return id.getProduto();
    }
}
