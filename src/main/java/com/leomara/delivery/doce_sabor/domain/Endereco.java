package com.leomara.delivery.doce_sabor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Endereco {
    private static final String MSG_OBRIGATORIO = "Preenchimento obrigatório.";

    @Id
    private Integer id;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 5, max = 200, message = "O tamanho deve ser entre 5 e 200 caracteres.")
    private String logradouro;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 1, max = 15, message = "O tamanho deve ser entre 1 e 15 caracteres.")
    private String numero;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 3, max = 200, message = "O tamanho deve ser entre 3 e 200 caracteres.")
    private String bairro;

    @Size(max = 200, message = "O tamanho máximo é de 200 caracteres.")
    private String complemento;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 9, max = 9, message = "O CEP é inválido.")
    private String cep;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 3, max = 200, message = "O tamanho deve ser entre 3 e 200 caracteres.")
    private String cidade;

    @NotBlank(message = MSG_OBRIGATORIO)
    @Size(min = 2, max = 2, message = "O tamanho deve ser de 2 caracteres.")
    private String uf;

    @JsonIgnore
    @OneToOne
    @MapsId
    private Cliente cliente;
}
