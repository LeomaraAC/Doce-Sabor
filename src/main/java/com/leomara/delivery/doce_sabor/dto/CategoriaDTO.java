package com.leomara.delivery.doce_sabor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {
    private Integer id;

    @NotBlank(message = "Preenchimento obrigatório.")
    @Size(min = 3, max = 50, message = "O tamanho deve ser entre 3 e 50 caracteres.")
    private String nome;
}
