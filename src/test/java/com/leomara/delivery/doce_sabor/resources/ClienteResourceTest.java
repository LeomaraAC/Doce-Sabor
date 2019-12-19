package com.leomara.delivery.doce_sabor.resources;

import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.domain.Endereco;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static com.leomara.delivery.doce_sabor.until.variables.ClienteVariables.*;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ClienteResourceTest extends ConfigurationResourceTests {


    private Cliente cliente;
    private Endereco endereco;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        endereco = new Endereco(null, LOGRADOURO, NUMERO, BAIRRO, "", CEP, CIDADE, UF, null);
        cliente = new Cliente(null, NOME, CPF, EMAIL, SENHA, endereco);
        cliente.getTelefones().addAll(Arrays.asList(TELEFONE));
//        endereco.setCliente(cliente);
    }

    /** Método insert */

    @Test
    public void deve_salvar_um_cliente_com_sucesso() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
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
                        "nome", equalTo(NOME),
                        "cpf", equalTo(CPF),
                        "senha", equalTo(SENHA),
                        "email", equalTo(EMAIL),
                        "telefones", contains(TELEFONE),
                        "endereco.cidade", equalTo(CIDADE),
                        "endereco.logradouro", equalTo(LOGRADOURO));
    }

    @Test
    public void deve_retornar_excecao_ao_salvar_cliente_sem_telefone() {
        cliente.getTelefones().clear();
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URI)
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("telefones"),
                        "errors.message", containsInAnyOrder(ERRO_TELEFONE_OBRIGATORIO));
    }

    @Test
    public void deve_retornar_excecao_ao_salvar_cliente_sem_logradouro_e_outros_campos_do_endereco_em_branco() {
//        endereco.setBairro("");
//        endereco.setCep("");
//        endereco.setCidade("");
//        endereco.setLogradouro("");
//        endereco.setNumero("");
//        endereco.setUf("");
//        endereco.setComplemento("");

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URI)
        .then()
                .log().body()
            .and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_VALIDACAO_DADOS),
                        "errors.field", containsInAnyOrder("logradouro", "numero", "bairro",
                                "cep", "cidade", "uf"));
    }

    @Test
    public void deve_salvar_cliente_ao_inves_de_atualizar_ao_receber_cliente_com_id() {
        cliente.setId(2);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", equalTo(5),
                        "endereco.logradouro", equalTo(LOGRADOURO));
    }

    @Test
    public void deve_retornar_excecao_ao_salvar_com_cpf_existente() {
        cliente.setCpf(CPF_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_CPF_CADASTRADO));
    }

    @Test
    public void deve_retornar_excecao_ao_salvar_com_email_existente() {
        cliente.setEmail(EMAIL_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URI)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_EMAIL_CADASTRADO));
    }

    /** Método Delete */
    @Test
    public void deve_retornar_excessao_ao_excluir_cliente_com_id_inexistente() {
        given()
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .delete(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(ERRO_CLIENTE_NAO_ENCONTRADO));
    }

    @Test
    public void deve_deletar_cliente_com_sucesso() {
        given()
                .pathParam("id",ID_EXISTENTE)
        .when()
                .delete(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    /** Método update */
    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_com_id_inexistente() {
        cliente.setId(ID_INEXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(ERRO_CLIENTE_NAO_ENCONTRADO));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_com_cpf_existente() {
        cliente.setCpf(CPF_EXISTENTE);
        cliente.setId(ID_EXISTENTE_3);

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
                .pathParam("id", ID_EXISTENTE_3)
        .when()
                .put(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_CPF_CADASTRADO));
    }

    @Test
    public void deve_retornar_excessao_ao_tentar_atualizar_com_email_existente() {
        cliente.setEmail(EMAIL_EXISTENTE);
        cliente.setId(ID_EXISTENTE_3);

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
                .pathParam("id", ID_EXISTENTE_3)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_EMAIL_CADASTRADO));
    }

    @Test
    public void deve_atualizar_com_sucesso() {
        cliente.setId(ID_EXISTENTE);
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
                .pathParam("id", ID_EXISTENTE)
        .when()
                .put(URI_COM_ID)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(ID_EXISTENTE),
                        "nome", equalTo(NOME),
                        "cpf", equalTo(CPF),
                        "senha", equalTo(SENHA),
                        "email", equalTo(EMAIL),
                        "telefones", contains(TELEFONE),
                        "endereco.cidade", equalTo(CIDADE),
                        "endereco.logradouro", equalTo(LOGRADOURO));
    }

    /** Método  find */
    @Test
    public void deve_retornar_erro_ao_nao_encontrar_cliente_pelo_id() {
        given()
                .pathParam("id", ID_INEXISTENTE)
        .when()
                .get(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("status", equalTo(HttpStatus.NOT_FOUND.value()),
                        "message", equalTo(ERRO_CLIENTE_NAO_ENCONTRADO));
    }

    @Test
    public void deve_retornar_cliente_pelo_id() {
        given()
                .pathParam("id", ID_EXISTENTE)
        .when()
                .get(URI_COM_ID)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(ID_EXISTENTE),
                        "cpf", equalTo(CPF_EXISTENTE),
                        "email", equalTo(EMAIL_EXISTENTE),
                        "nome", equalTo(NOME_EXISTE),
                        "endereco.bairro", equalTo(BAIRRO_EXISTENTE),
                        "telefones", containsInAnyOrder(TELEFONES_EXISTENTES.toArray()));
    }

    /** Busca paginada */
    @Test
    public void deve_retornar_todos_os_clientes_paginados(){
        get(URI)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.cpf", containsInAnyOrder(CPF_TODOS_CLIENTES.toArray()),
                        "totalElements", equalTo(CPF_TODOS_CLIENTES.size()),
                        "empty", equalTo(false));
    }

    @Test
    public void deve_retornar_os_clientes_que_contem_ira_no_nome(){
        given()
                .param("filterNome", "ira")
        .when()
                .get(URI)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("content.cpf", containsInAnyOrder(CPF_CLIENTES_IRA.toArray()),
                        "totalElements", equalTo(CPF_CLIENTES_IRA.size()));
    }

    @Test
    public void deve_retornar_content_vazio_ao_buscar_clientes_que_contem_itd() {
        given()
                .param("filterNome", "itd")
        .when()
                .get(URI)
        .then()
                .statusCode(HttpStatus.OK.value())
                .body("content", is(empty()),
                        "totalElements", equalTo(0));
    }

    @Test
    public void deve_retornar_a_paginacao_com_1_cliente_por_pagina_ordenado_pelo_id_decrescente_e_na_pagina_2(){
        given()
                .param("page", 2)
                .param("linesPerPage", 1)
                .param("orderBy", "id")
                .param("direction", "DESC")
        .when()
                .get(URI)
        .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .body("content.cpf", contains(CPF_EXISTENTE_2),
                        "last", equalTo(false),
                        "totalPages", equalTo(4),
                        "first", equalTo(false),
                        "numberOfElements", equalTo(1),
                        "empty", equalTo(false));
    }
}
