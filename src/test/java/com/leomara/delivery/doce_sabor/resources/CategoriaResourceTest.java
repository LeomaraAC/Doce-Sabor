package com.leomara.delivery.doce_sabor.resources;


import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CategoriaResourceTest extends ConfigurationResourceTests {

    private Categoria categoria;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        categoria = new Categoria();
        categoria.setNome("Comidas nordestina");
    }

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

    @Test
    public void deve_salvar_uma_nova_pessoa() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(categoria))
                .when()
                .post("categorias")
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
}
