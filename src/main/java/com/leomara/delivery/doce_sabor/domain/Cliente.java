package com.leomara.delivery.doce_sabor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cliente {
    private static final String MSG_OBRIGATORIO = "Preenchimento obrigatório.";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 3, max = 50, message = "O tamanho deve ser entre 3 e 50 caracteres.")
    private String nome;

    @NotBlank(message = MSG_OBRIGATORIO)
    @CPF(message = "O CPF é inválido.")
    private String cpf;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Email(message = "O email é inválido.")
    private String email;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 8, max = 200, message = "O tamanho deve ser entre 8 e 200 caracteres.")
    private String senha;

    @ElementCollection
    @CollectionTable(name = "Telefone")
    @Size(min = 1, message = "É obrigatório informar pelo menos um telefone.")
    private Set<String> telefones = new HashSet<>();

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Classificacao> classificacaos = new ArrayList<>();

    public Cliente(Integer id, String nome, String cpf, String email, String senha, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }
}
