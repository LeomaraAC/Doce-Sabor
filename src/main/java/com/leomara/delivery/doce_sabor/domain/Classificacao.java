package com.leomara.delivery.doce_sabor.domain;

import com.leomara.delivery.doce_sabor.domain.PK.ClassificacaoPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class Classificacao {

    @EmbeddedId
    private ClassificacaoPK id;
    private Integer nota;
    private String obs;

    public Classificacao() {}

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

    public ClassificacaoPK getId() {
        return id;
    }

    public void setId(ClassificacaoPK id) {
        this.id = id;
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
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
