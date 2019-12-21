package com.leomara.delivery.doce_sabor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Categoria {

    @ApiModelProperty(value = "ID da categoria")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Nome  da categoria")
    @NotBlank(message = "Preenchimento obrigatório.")
    @Size(min = 3, max = 50, message = "O tamanho deve ser entre 3 e 50 caracteres.")
    private String nome;

    @ApiModelProperty(value = "Lista de produtos pertencentes a categoria")
    @OneToMany(mappedBy = "categoria")
    private List<Produto> produtos = new ArrayList<>();

    public Categoria(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
