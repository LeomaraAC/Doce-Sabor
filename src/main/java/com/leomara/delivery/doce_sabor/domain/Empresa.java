package com.leomara.delivery.doce_sabor.domain;

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
public class Empresa {

    @ApiModelProperty(value = "ID da empresa")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Nome fantasia da empresa")
    private String nome_fantasia;

    @ApiModelProperty(value = "CNPJ da empresa")
    private String cnpj;

    @ApiModelProperty(value = "Email da empresa")
    private String email;

    @ApiModelProperty(value = "Senha da empresa")
    private String senha;

    @ApiModelProperty(value = "Lista de produtos da empresa")
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos = new ArrayList<>();

    public Empresa(Integer id, String nome_fantasia, String cnpj, String email, String senha) {
        this.id = id;
        this.nome_fantasia = nome_fantasia;
        this.cnpj = cnpj;
        this.email = email;
        this.senha = senha;
    }
}
