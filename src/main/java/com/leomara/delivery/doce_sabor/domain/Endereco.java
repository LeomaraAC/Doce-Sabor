package com.leomara.delivery.doce_sabor.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String logradouro;
    private String numero;
    private String bairro;
    private String complemento;
    private String cep;
    private String cidade;
    private String uf;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
