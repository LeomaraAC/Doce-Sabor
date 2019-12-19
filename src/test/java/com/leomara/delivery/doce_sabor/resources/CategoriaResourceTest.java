package com.leomara.delivery.doce_sabor.resources;


import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.leomara.delivery.doce_sabor.until.variables.CategoriaVariables.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CategoriaResourceTest extends ConfigurationResourceTests {

    private Categoria categoria;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        categoria = new Categoria();
        categoria.setNome(NOME);
    }

    /** FindById */
    @Test
    public void deve_buscar_categoria_pelo_id(){
       given()
               .pathParam("id", ID_EXISTENTE)
       .get(URI_COM_ID)
       .then()
               .log().body().and()
               .statusCode(HttpStatus.OK.value())
               .body("id", equalTo(ID_EXISTENTE),
                       "nome", equalTo(NOME_EXISTENTE),
                        "produtos.nome", containsInAnyOrder(PRODUTOS.toArray()));
    }

    @Test
    public void deve_retornar_erro_ao_nao_encontrar_categoria() {
       given()
               .pathParam("id", ID_INEXISTENTE)
       .get(URI_COM_ID)
       .then()
               .log().body().and()
               .statusCode(HttpStatus.NOT_FOUND.value())
               .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                       "message", equalTo(ERRO_CAT_NAO_ENCONTRADA));
    }

    /** Insert */
    @Test
    public void deve_salvar_uma_nova_pessoa() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+ porta + URI + ID_NOVO))
                .body("id", equalTo(ID_NOVO),
                        "nome",equalTo(NOME),
                        "produtos.nome", is(empty()));

    }

    @Test
    public void nao_deve_salvar_duas_categorias_com_o_mesmo_nome() {
        categoria.setNome(NOME_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_CAT_EXISTENTE));
    }

    @Test
    public void nao_deve_salvar_categoria_com_menos_de_tres_letras() {
        categoria.setNome(NOME_PEQUENO);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder(ERRO_TAMANHO_ENTRE_3_E_50));
    }

    @Test
    public void nao_deve_salvar_categoria_com_mais_de_cinquenta_letras() {
        categoria.setNome(NOME_GRANDE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder(ERRO_TAMANHO_ENTRE_3_E_50));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_tendo_somente_espacos(){
        categoria.setNome(STRING_VAZIA_COM_ESPACO);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder(ERRO_PREENCHIMENTO_OBRIGATORIO));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_vazio(){
        categoria.setNome(STRING_VAZIA);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems(ERRO_PREENCHIMENTO_OBRIGATORIO));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_null() {
        categoria.setNome(null);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems(ERRO_PREENCHIMENTO_OBRIGATORIO));
    }

    @Test
    public void deve_inserir_ao_inves_de_atualizar_quando_categoria_chegar_com_id() {
        categoria.setId(ID_EXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post(URI)
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+ porta + URI + ID_NOVO))
                .body("id", equalTo(5),
                        "nome",equalTo(NOME),
                        "produtos.nome", is(empty()));
    }

    /** Update */
    @Test
    public void deve_atualizar_com_sucesso_uma_categoria() {
        categoria.setId(ID_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .pathParam("id", ID_EXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(ID_EXISTENTE),
                        "nome", equalTo(NOME),
                        "produtos.nome", containsInAnyOrder(PRODUTOS.toArray()));
    }

    @Test
    public void deve_retornar_erro_ao_tentar_atualizar_categoria_com_nome_ja_existente() {
        categoria.setId(ID_EXISTENTE_3);
        categoria.setNome(NOME_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .pathParam("id", ID_EXISTENTE_3)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_CAT_EXISTENTE));
    }

    @Test
    public void deve_retornar_erro_ao_atualizar_com_nome_vazio() {
        categoria.setId(ID_EXISTENTE);
        categoria.setNome(STRING_VAZIA);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .pathParam("id", ID_EXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems(ERRO_PREENCHIMENTO_OBRIGATORIO));
    }

    @Test
    public void deve_retornar_erro_ao_atualizar_com_nome_menor_que_tres_caracteres() {
        categoria.setId(ID_EXISTENTE);
        categoria.setNome(NOME_PEQUENO);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .pathParam("id", ID_EXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder(ERRO_TAMANHO_ENTRE_3_E_50));
    }

    @Test
    public void deve_retornar_erro_ao_tentar_atualizar_categoria_com_id_inexistente(){
        categoria.setId(ID_INEXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(ERRO_CAT_NAO_ENCONTRADA));
    }

    /** Delete */
    @Test
    public void deve_deletar_uma_categoria_com_sucesso() {
        given()
                .pathParam("id", ID_CAT_SEM_PRODUTO)
        .when()
                .delete(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deve_retornar_erro_ao_excluir_categoria_com_produtos() {
        given()
                .pathParam("id", ID_EXISTENTE)
        .when()
                .delete(URI_COM_ID)
        .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_EXCUIR_CAT_COM_PRODUTO));
    }

    @Test
    public void deve_retornar_erro_ao_excluir_categoria_inexistente() {
        given()
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .delete(URI_COM_ID)
        .then()
                .log().body()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(ERRO_CAT_NAO_ENCONTRADA));
    }

    /**Busca paginada */
    @Test
    public void deve_retornar_todas_as_categorias_paginada() {
        get(URI)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("content.nome", containsInAnyOrder(TODAS_CAT.toArray()),
                        "totalElements", equalTo(TODAS_CAT.size()),
                        "empty", equalTo(false));
    }

    @Test
    public void deve_retornar_as_categorias_que_contem_as_letras_os_paginado() {
        given()
                .param("nome", "os")
        .get(URI)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("content.nome", containsInAnyOrder(CAT_COM_OS.toArray()),
                        "totalElements", equalTo(CAT_COM_OS.size()));
    }

    @Test
    public void deve_retornar_a_paginacao_com_3_categorias_por_pagina_ordenado_pelo_id_decrescente_e_na_pagina_2() {
        given()
                .param("page", 1)
                .param("linesPerPage", 3)
                .param("orderBy", "id")
                .param("direction", "DESC")
        .get(URI)
        .then()
                .log().body()
                .statusCode(HttpStatus.OK.value())
                .body("size", equalTo(3),
                        "number", equalTo(1),
                        "numberOfElements", equalTo(2),
                        "content.nome", containsInAnyOrder("Salgados", "Comida Vegana"));
    }
}
