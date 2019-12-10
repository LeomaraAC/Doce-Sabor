package com.leomara.delivery.doce_sabor.until;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class Variables {
    public static final String STRING_VAZIA_COM_ESPACO = "   ";
    public static final String STRING_VAZIA = "";

    /** Page */
    public static final Integer PAGE = 0;
    public static final Integer LINES_PER_PAGE = 10;
    public static final String  DIRECTION = "ASC";

    /** Erros */
    public static final String ERRO_VALIDACAO_DADOS = "Erro na validação dos dados.";
    public static final String ERRO_TAMANHO_ENTRE_3_E_50 = "O tamanho deve ser entre 3 e 50 caracteres.";
    public static final String ERRO_PREENCHIMENTO_OBRIGATORIO = "Preenchimento obrigatório.";
    public static final String ERRO_ID_OBRIGATORIO = "O campo id é obrigatório.";



}