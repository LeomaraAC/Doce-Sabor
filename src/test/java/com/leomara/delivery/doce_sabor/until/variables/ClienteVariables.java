package com.leomara.delivery.doce_sabor.until.variables;

import com.leomara.delivery.doce_sabor.until.Variables;

public class ClienteVariables extends Variables {
    /** URN */
    public static final String URN = "/clientes/";
    /** Cliente */
    public static final Integer ID_NOVO = 5;
    public static final Integer ID_EXISTENTE = 1;
    public static final Integer ID_INEXISTENTE = 20;
    public static final String CPF_EXISTENTE = "69811291322";
    public static final String EMAIL_EXISTENTE = "luciabc@tiger.com";

    public static final String NOME = "Augusto Pedro Breno Aparício";
    public static final String CPF = "06433861652";
    public static final String EMAIL = "augustopedrobreno@outloock.com.br";
    public static final String SENHA = "sNcIlx9P5m";
    public static final String TELEFONE = "(83) 2581-8319";

    public static final String LOGRADOURO = "Vila Alice";
    public static final String NUMERO = "178";
    public static final String BAIRRO = "Centro";
    public static final String COMPLEMENTO = "Apto 203";
    public static final String CEP = "58300-440";
    public static final String CIDADE = "Santa Rita";
    public static final String UF = "PB";

    /** Erros Cliente */
    public static final String ERRO_TELEFONE_OBRIGATORIO = "É obrigatório informar pelo menos um telefone.";
    public static final String ERRO_CPF_CADASTRADO = "O CPF " + CPF_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_EMAIL_CADASTRADO = "O email " + EMAIL_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado. ID: " + ID_EXISTENTE;
}
