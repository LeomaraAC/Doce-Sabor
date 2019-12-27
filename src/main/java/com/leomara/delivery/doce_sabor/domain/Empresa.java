package com.leomara.delivery.doce_sabor.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Empresa {
    private static final String MSG_OBRIGATORIO = "Preenchimento obrigatório.";

    @ApiModelProperty(value = "ID da empresa")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ApiModelProperty(value = "Nome fantasia da empresa")
    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 3, max = 200, message = "O tamanho deve ser entre 3 e 200 caracteres.")
    private String nome_fantasia;

    @ApiModelProperty(value = "CNPJ da empresa")
    @NotBlank(message = MSG_OBRIGATORIO)
    @CNPJ(message = "O CNPJ é inválido.")
    private String cnpj;

    @ApiModelProperty(value = "Email da empresa")
    @Email(message = "O email é inválido.")
    @NotBlank(message = MSG_OBRIGATORIO)
    private String email;

    @ApiModelProperty(value = "Senha da empresa")
    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 8, max = 200, message = "O tamanho deve ser entre 8 e 200 caracteres.")
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
