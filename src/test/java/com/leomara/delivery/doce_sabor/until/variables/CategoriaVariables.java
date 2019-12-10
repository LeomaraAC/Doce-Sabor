package com.leomara.delivery.doce_sabor.until.variables;

import com.leomara.delivery.doce_sabor.until.Variables;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public class CategoriaVariables extends Variables {


    /** URN */
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

    /** Page */
    public static final String ORDER_BY = "nome";
    public static final PageRequest PAGE_REQUEST = PageRequest.of(PAGE, LINES_PER_PAGE,
            Sort.Direction.valueOf(DIRECTION), ORDER_BY);

    /** Produtos */
    public static final String PRODUTO_1 = "Produto 1";
    public static final String PRODUTO_2 = "Produto 2";

    /** Erros Categorias */
    public static final String ERRO_CAT_NAO_ENCONTRADA = "Categoria não encontrada. ID: " + ID_CAT_INEXISTENTE;
    public static final String ERRO_CAT_EXISTENTE = "A categoria " + NOME_CAT_EXISTENTE + " já esta cadastrada.";
    public static final String ERRO_EXCUIR_CAT_COM_PRODUTO = "Não é possível excluir uma categoria que possui produtos.";
}
