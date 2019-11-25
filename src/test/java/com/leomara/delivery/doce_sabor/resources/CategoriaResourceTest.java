package com.leomara.delivery.doce_sabor.resources;


import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.dto.CategoriaDTO;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CategoriaResourceTest extends ConfigurationResourceTests {

    private CategoriaDTO categoria;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        categoria = new CategoriaDTO();
        categoria.setNome("Comidas nordestina");
    }

    /** FindById */
    @Test
    public void deve_buscar_categoria_pelo_id(){
       given()
               .pathParam("id", 2)
       .get("/categorias/{id}")
       .then()
               .log().body().and()
               .statusCode(HttpStatus.OK.value())
               .body("id", equalTo(2),
                       "nome", equalTo("Doces"),
                        "produtos.nome", containsInAnyOrder("Canjica", "Trufas", "Pavê de chocolate branco"));
    }

    @Test
    public void deve_retornar_erro_ao_nao_encontrar_categoria() {
       given()
               .pathParam("id", 10)
       .get("/categorias/{id}")
       .then()
               .log().body().and()
               .statusCode(HttpStatus.NOT_FOUND.value())
               .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                       "message", equalTo("Categoria não encontrada. ID: 10"));
    }

    /** Insert */
    @Test
    public void deve_salvar_uma_nova_pessoa() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+ porta +"/categorias/5"))
                .body("id", equalTo(5),
                        "nome",equalTo("Comidas nordestina"),
                        "produtos.nome", is(empty()));

    }

    @Test
    public void nao_deve_salvar_duas_categorias_com_o_mesmo_nome() {
        categoria.setNome("Bolos");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("A categoria Bolos já esta cadastrada."));
    }

    @Test
    public void nao_deve_salvar_categoria_com_menos_de_tres_letras() {
        categoria.setNome("ab");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder("O tamanho deve ser entre 3 e 50 caracteres."));
    }

    @Test
    public void nao_deve_salvar_categoria_com_mais_de_cinquenta_letras() {
        categoria.setNome("Bolo Festa PavêDechocolate branco Tapioca de frango");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder("O tamanho deve ser entre 3 e 50 caracteres."));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_tendo_somente_espacos(){
        categoria.setNome("   ");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder("Preenchimento obrigatório."));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_vazio(){
        categoria.setNome("");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems("Preenchimento obrigatório."));
    }

    @Test
    public void nao_deve_salvar_categoria_com_nome_null() {
        categoria.setNome(null);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems("Preenchimento obrigatório."));
    }

    @Test
    public void deve_inserir_ao_inves_de_atualizar_quando_categoria_chegar_com_id() {
        categoria.setId(3);

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .post("/categorias")
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+ porta +"/categorias/5"))
                .body("id", equalTo(5),
                        "nome",equalTo("Comidas nordestina"),
                        "produtos.nome", is(empty()));
    }

    /** Update */
    @Test
    public void deve_atualizar_com_sucesso_uma_categoria() {
        categoria.setId(3);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(3),
                        "nome", equalTo("Comidas nordestina"),
                        "produtos.nome", containsInAnyOrder("Pão de cebola", "Pão de alho"));
    }

    @Test
    public void deve_retornar_erro_ao_tentar_atualizar_categoria_com_nome_ja_existente() {
        categoria.setId(3);
        categoria.setNome("Salgados");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("A categoria Salgados já esta cadastrada."));
    }

    @Test
    public void deve_retornar_erro_ao_atualizar_com_nome_vazio() {
        categoria.setId(3);
        categoria.setNome("");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", hasItems("nome"),
                        "errors.message", hasItems("Preenchimento obrigatório."));
    }

    @Test
    public void deve_retornar_erro_ao_atualizar_com_nome_menor_que_tres_caracteres() {
        categoria.setId(3);
        categoria.setNome("ab");
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Erro na validação dos dados."),
                        "errors.field", containsInAnyOrder("nome"),
                        "errors.message", containsInAnyOrder("O tamanho deve ser entre 3 e 50 caracteres."));
    }

    @Test
    public void deve_retornar_erro_ao_tentar_atualizar_categoria_com_id_inexistente(){
        categoria.setId(100);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo("Categoria não encontrada. ID: 100"));
    }

    @Test
    public void deve_retornar_erro_ao_tentar_atualizar_categoria_com_id_nulo(){
        categoria.setId(null);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
        .when()
                .put("/categorias")
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("O campo id é obrigatório."));
    }

    /** Delete */
    @Test
    public void deve_deletar_uma_categoria_com_sucesso() {
        given()
                .pathParam("id", 0)
        .when()
                .delete("/categorias/{id}")
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deve_retornar_erro_ao_excluir_categoria_com_produtos() {
        given()
                .pathParam("id", 2)
        .when()
                .delete("/categorias/{id}")
        .then()
                .log().body()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo("Não é possível excluir uma categoria que possui produtos."));
    }
    
    @Test
    public void deve_retornar_erro_ao_excluir_categoria_inexistente() {
        given()
                .pathParam("id", 10)
        .when()
                .delete("/categorias/{id}")
        .then()
                .log().body()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo("Categoria não encontrada. ID: 10"));
    }
}
