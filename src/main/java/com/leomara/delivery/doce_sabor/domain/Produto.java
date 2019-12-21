package com.leomara.delivery.doce_sabor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "ID do produto")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Nome do produto")
    private String nome;

    @ApiModelProperty(value = "Preço do produto")
    private Double valor;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Classificacao> classificacaos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensPedido> itensPedidos = new ArrayList<>();

    public Produto(Integer id, String nome, Double valor, Empresa empresa, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.empresa = empresa;
        this.categoria = categoria;
    }
}
