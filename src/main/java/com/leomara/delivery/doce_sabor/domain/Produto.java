package com.leomara.delivery.doce_sabor.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "id.produto")
    private List<Classificacao> classificacaos = new ArrayList<>();

    @OneToMany(mappedBy = "id.produto")
    private List<ItensPedido> itensPedidos = new ArrayList<>();

    public Produto() {}

    public Produto(Integer id, String nome, Double valor, Empresa empresa, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.empresa = empresa;
        this.categoria = categoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Classificacao> getClassificacaos() {
        return classificacaos;
    }

    public void setClassificacaos(List<Classificacao> classificacaos) {
        this.classificacaos = classificacaos;
    }

    public List<ItensPedido> getItensPedidos() {
        return itensPedidos;
    }

    public void setItensPedidos(List<ItensPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id.equals(produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
