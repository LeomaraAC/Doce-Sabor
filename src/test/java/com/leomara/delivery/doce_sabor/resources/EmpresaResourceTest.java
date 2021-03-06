package com.leomara.delivery.doce_sabor.resources;

import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.leomara.delivery.doce_sabor.until.Variables.ERRO_PREENCHIMENTO_OBRIGATORIO;
import static com.leomara.delivery.doce_sabor.until.Variables.ERRO_VALIDACAO_DADOS;
import static com.leomara.delivery.doce_sabor.until.variables.EmpresaVariables.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class EmpresaResourceTest extends ConfigurationResourceTests {
    private Gson gson;
    private Empresa empresa;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        empresa = new Empresa(null, NOME_FANTASIA, CNPJ, EMAIL, SENHA);
    }

    /** Inserir */
    @Test
    public void deve_salvar_uma_empresa_om_sucesso() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:" + porta + URN + ID_NOVO))
                .body("id", equalTo(ID_NOVO),
                        "nome_fantasia", equalTo(NOME_FANTASIA),
                        "cnpj", equalTo(CNPJ),
                        "senha", equalTo(SENHA));
    }

    @Test
    public void deve_retornar_excessao_ao_inserir_cnpj_ja_existente() {
        empresa.setCnpj(CNPJ_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_CNPJ));
    }

    @Test
    public void deve_retornar_excessao_ao_inserir_email_ja_existente() {
        empresa.setEmail(EMAIL_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_EMAIL));
    }

    @Test
    public void deve_retornar_excessao_ao_inserir_nome_ja_existente() {
        empresa.setNome_fantasia(NOME_FANTASIA_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_NOME_FANTASIA));
    }

    @Test
    public void deve_salvar_ao_inves_de_atualizar_ao_receber_empresa_com_id_existente() {
        empresa.setId(ID_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:" + porta + URN + ID_NOVO))
                .body("id", equalTo(ID_NOVO),
                        "nome_fantasia", equalTo(NOME_FANTASIA));
    }

    @Test
    public void deve_retornar_excessao_ao_inserir_com_cnpj_invalido() {
        empresa.setCnpj(CNPJ_INVALIDO);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("cnpj"),
                        "errors.message", containsInAnyOrder(MSG_ERRO_CNPJ_INVALIDO));
    }

    @Test
    public void deve_retornar_excessao_ao_inserir_com_email_invalido() {
        empresa.setEmail(EMAIL_INVALIDO);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("email"),
                        "errors.message", containsInAnyOrder(MSG_ERRO_EMAIL_INVALIDO));
    }

    @Test
    public void deve_retornar_excessao_de_campos_obrigatorios() {
        empresa.setEmail("");
        empresa.setCnpj("");
        empresa.setNome_fantasia("");
        empresa.setSenha("");

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(empresa))
        .when()
                .post(URN)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", hasItems("email", "cnpj", "nome_fantasia", "senha"),
                        "errors.message", hasItems(ERRO_PREENCHIMENTO_OBRIGATORIO));
    }

    /** Deletar */
    @Test
    public void deve_retornar_excessao_ao_tenta_apagar_empresa_com_id_inexistente() {
        given()
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .delete(URN_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(MSG_ERRO_EMPRESA_NAO_ENCONTRADA));
    }

    @Test
    public void deve_apagar_uma_empresa_com_sucesso() {
        given()
                .pathParam("id", ID_EXISTENTE)
        .when()
                .delete(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /** Ataualizar */
    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_id_inexistente() {
        empresa.setId(ID_INEXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_INEXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(MSG_ERRO_EMPRESA_NAO_ENCONTRADA));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_CNPJ_ja_existente() {
        empresa.setId(ID_EXISTENTE);
        empresa.setCnpj(CNPJ_EXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_CNPJ));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_CNPJ_invalido() {
        empresa.setId(ID_EXISTENTE);
        empresa.setCnpj(CNPJ_INVALIDO);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("cnpj"),
                        "errors.message", containsInAnyOrder(MSG_ERRO_CNPJ_INVALIDO));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_email_ja_existente() {
        empresa.setId(ID_EXISTENTE);
        empresa.setEmail(EMAIL_EXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_EMAIL));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_email_invalido() {
        empresa.setId(ID_EXISTENTE);
        empresa.setEmail(EMAIL_INVALIDO);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("email"),
                        "errors.message", containsInAnyOrder(MSG_ERRO_EMAIL_INVALIDO));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_empresa_com_nome_fantasia_ja_existente() {
        empresa.setId(ID_EXISTENTE);
        empresa.setNome_fantasia(NOME_FANTASIA_EXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(MSG_ERRO_DUPLICIDADE_NOME_FANTASIA));
    }

    @Test
    public void deve_atualizar_empresa_de_id_3_ao_inves_do_id_4() {
        empresa.setId(ID_NOVO); // 4

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE) // 3
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(ID_EXISTENTE),
                        "nome_fantasia", equalTo(NOME_FANTASIA));
    }

    @Test
    public void deve_atualizar_empresa_com_sucesso() {
        empresa.setId(ID_EXISTENTE);

        given()
                .request()
                .contentType(ContentType.JSON)
                .pathParam("id", ID_EXISTENTE)
                .body(gson.toJson(empresa))
        .when()
                .put(URN_COM_ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(ID_EXISTENTE),
                        "nome_fantasia", equalTo(NOME_FANTASIA),
                        "cnpj", equalTo(CNPJ),
                        "email", equalTo(EMAIL));
    }
}

