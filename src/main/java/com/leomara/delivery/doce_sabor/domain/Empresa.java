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
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome_fantasia;
    private String cnpj;
    private String email;
    private String senha;

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
