package com.leomara.delivery.doce_sabor.until.variables;

import com.leomara.delivery.doce_sabor.until.Variables;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class ClienteVariables extends Variables {
    /** URN */
    public static final String URN = "/clientes/";
    public static final String URN_ID = URN +"{id}";

    /** LISTA */
    public static List CPF_TODOS_CLIENTES = Arrays.asList("69811291322", "94202677331", "23179425679", "22088847448");
    public static List CPF_CLIENTES_IRA = Arrays.asList("22088847448", "94202677331");
    public static final List<String> TELEFONES_EXISTENTES = Arrays.asList("(19) 2987-8860", "(19) 98386-7407");

    /** ID */
    public static final Integer ID_NOVO = 5;
    public static final Integer ID_EXISTENTE = 1;
    public static final Integer ID_EXISTENTE_3 = 3;
    public static final Integer ID_INEXISTENTE = 20;

    /** CPF */
    public static final String CPF_EXISTENTE = "69811291322";
    public static final String CPF = "06433861652";

    /** EMAIL */
    public static final String EMAIL_EXISTENTE = "luciabc@tiger.com";
    public static final String EMAIL = "augustopedrobreno@outloock.com.br";

    /** NOME */
    public static final String NOME = "Augusto Pedro Breno Aparício";
    public static final String NOME_EXISTE = "Lúcia Benedita Cardoso";
    public static final String STRING_FILTRO = "ira";

    public static final String SENHA = "sNcIlx9P5m";
    public static final String TELEFONE = "(83) 2581-8319";

    /** ENDEREÇO */
    public static final String LOGRADOURO = "Vila Alice";
    public static final String NUMERO = "178";
    public static final String BAIRRO = "Centro";
    public static final String BAIRRO_EXISTENTE = "Sítios de Recreio Gramado";
    public static final String COMPLEMENTO = "Apto 203";
    public static final String CEP = "58300-440";
    public static final String CIDADE = "Santa Rita";
    public static final String UF = "PB";

    /** Page */
    public static final String ORDER_BY = "nome";
    public static final PageRequest PAGE_REQUEST = PageRequest.of(PAGE, LINES_PER_PAGE,
            Sort.Direction.valueOf(DIRECTION), ORDER_BY);

    /** Erros */
    public static final String ERRO_TELEFONE_OBRIGATORIO = "É obrigatório informar pelo menos um telefone.";
    public static final String ERRO_CPF_CADASTRADO = "O CPF " + CPF_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_EMAIL_CADASTRADO = "O email " + EMAIL_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado. ID: " + ID_INEXISTENTE;
}
