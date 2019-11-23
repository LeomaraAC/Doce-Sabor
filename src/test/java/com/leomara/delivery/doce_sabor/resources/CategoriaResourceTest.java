package com.leomara.delivery.doce_sabor.resources;


import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CategoriaResourceTest extends ConfigurationResourceTests {

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


}
