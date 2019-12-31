package com.leomara.delivery.doce_sabor.until.variables;

public class EmpresaVariables {

    /** URN */
    public static final String URN = "/api/empresas/";

    /** ID */
    public static final Integer ID_INEXISTENTE = 10;
    public static final Integer ID_NOVO = 4;
    public static final Integer ID_EXISTENTE = 3;

    /** NOME FANTASIA */
    public static final String NOME_FANTASIA = "RedRock";
    public static final String NOME_FANTASIA_EXISTENTE = "Sabor Caseiro";

    /** CNPJ */
    public static final String CNPJ_EXISTENTE = "36112464000137";
    public static final String CNPJ_INVALIDO = "361137";
    public static final String CNPJ = "19712232000109";

    /** EMAIL */
    public static final String EMAIL = "sistema@louiseemariahadegame.com.br";
    public static final String EMAIL_EXISTENTE = "contact@saborcaseiro.com.br";
    public static final String EMAIL_INVALIDO = "contactsaborcaseiro.@.com.br";

    /** SENHA */
    public static final String SENHA = "C2BfxHCL";

    /** ERROS */
    public static final String MSG_ERRO_DUPLICIDADE_NOME_FANTASIA = "O nome fantasia "+NOME_FANTASIA_EXISTENTE+" já esta cadastrado.";
    public static final String MSG_ERRO_DUPLICIDADE_EMAIL = "O email "+ EMAIL_EXISTENTE +" já esta cadastrado.";
    public static final String MSG_ERRO_DUPLICIDADE_CNPJ = "O CNPJ "+CNPJ_EXISTENTE+" já esta cadastrado.";
    public static final String MSG_ERRO_CNPJ_INVALIDO = "O CNPJ é inválido.";
    public static final String MSG_ERRO_EMAIL_INVALIDO = "O email é inválido.";
    public static final String MSG_ERRO_EMPRESA_NAO_ENCONTRADA = "Empresa não encontrada. ID: " + ID_INEXISTENTE;
}
