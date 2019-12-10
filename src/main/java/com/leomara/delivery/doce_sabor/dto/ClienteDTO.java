package com.leomara.delivery.doce_sabor.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ClienteDTO {
    private static final String MSG_OBRIGATORIO = "Preenchimento obrigatório.";

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

    @Size(min = 1, message = "É obrigatório informar pelo menos um telefone.")
    private Set<String> telefones = new HashSet<>();

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

}
