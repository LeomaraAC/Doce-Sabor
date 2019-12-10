package com.leomara.delivery.doce_sabor.until.Variables;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class Variables {
    public static final String STRING_VAZIA_COM_ESPACO = "   ";
    public static final String STRING_VAZIA = "";

    /** URN */
    public static final String URN_CLIENTE = "/clientes/";
    public static final String URN_CATEGORIA = "/categorias/";
    public static final String URN_CATEGORIA_ID = URN_CATEGORIA + "{id}";

    /** Categoria */
    public static final Integer ID_CAT_EXISTENTE = 2;
    public static final Integer ID_CAT_SEM_PRODUTO = 0;
    public static final Integer ID_CAT_EXISTENTE_3 = 3;
    public static final Integer ID_CAT_INEXISTENTE = ID_CAT_EXISTENTE + 10;
    public static final String NOME_CAT_EXISTENTE = "Doces";
    public static final List<String> CAT_PRODUTOS = Arrays.asList("Canjica", "Trufas", "Pavê de chocolate branco");
    public static final Integer ID_NOVA_CAT = 5;
    public static final String NOME_CAT = "Comidas nordestina";
    public static final String NOME_PEQUENO_CAT = "ab";
    public static final String NOME_GRANDE_CAT = "Bolo Festa PavêDechocolate branco Tapioca de frango";
    public static final List<String> TODAS_CAT = Arrays.asList("Comida Vegana", "Salgados", "Pães", "Doces", "Bolos");
    public static final List<String> CAT_COM_OS = Arrays.asList("Salgados", "Bolos");

    /** Page Categoria e Cliente */
    public static final Integer PAGE = 0;
    public static final Integer LINES_PER_PAGE = 10;
    public static final String ORDER_BY = "nome";
    public static final String  DIRECTION = "ASC";
    public static final PageRequest PAGE_REQUEST = PageRequest.of(PAGE, LINES_PER_PAGE,
            Sort.Direction.valueOf(DIRECTION), ORDER_BY);

    /** Cliente */
    public static final Integer ID_NOVO_CLIENTE = 5;
    public static final Integer ID_CLIENTE_EXISTENTE = 1;
    public static final String CPF_EXISTENTE = "69811291322";
    public static final String EMAIL_EXISTENTE = "luciabc@tiger.com";

    public static final String NOME_CLIENTE = "Augusto Pedro Breno Aparício";
    public static final String CPF_CLIENTE = "06433861652";
    public static final String EMAIL_CLIENTE = "augustopedrobreno@outloock.com.br";
    public static final String SENHA_CLIENTE = "sNcIlx9P5m";
    public static final String TELEFONE_CLIENTE = "(83) 2581-8319";

    public static final String LOGRADOURO = "Vila Alice";
    public static final String NUMERO = "178";
    public static final String BAIRRO = "Centro";
    public static final String COMPLEMENTO = "Apto 203";
    public static final String CEP = "58300-440";
    public static final String CIDADE = "Santa Rita";
    public static final String UF = "PB";

    /** Produtos */

    public static final String PRODUTO_1 = "Produto 1";
    public static final String PRODUTO_2 = "Produto 2";

    /** Erros */
    public static final String ERRO_VALIDACAO_DADOS = "Erro na validação dos dados.";
    public static final String ERRO_TAMANHO_ENTRE_3_E_50 = "O tamanho deve ser entre 3 e 50 caracteres.";
    public static final String ERRO_PREENCHIMENTO_OBRIGATORIO = "Preenchimento obrigatório.";
    public static final String ERRO_ID_OBRIGATORIO = "O campo id é obrigatório.";

    /** Erros Cliente */
    public static final String ERRO_TELEFONE_OBRIGATORIO = "É obrigatório informar pelo menos um telefone.";
    public static final String ERRO_CPF_CADASTRADO = "O CPF " + CPF_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_EMAIL_CADASTRADO = "O email " + EMAIL_EXISTENTE + " já esta cadastrado.";
    public static final String ERRO_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado. ID: " + ID_CLIENTE_EXISTENTE;

    /** Erros Categorias */
    public static final String ERRO_CAT_NAO_ENCONTRADA = "Categoria não encontrada. ID: " + ID_CAT_INEXISTENTE;
    public static final String ERRO_CAT_EXISTENTE = "A categoria " + NOME_CAT_EXISTENTE + " já esta cadastrada.";
    public static final String ERRO_EXCUIR_CAT_COM_PRODUTO = "Não é possível excluir uma categoria que possui produtos.";
}