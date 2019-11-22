package com.leomara.delivery.doce_sabor.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
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

    public Produto(Integer id, String nome, Double valor, Empresa empresa, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.empresa = empresa;
        this.categoria = categoria;
    }
}
