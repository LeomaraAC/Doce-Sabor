package com.leomara.delivery.doce_sabor.resources;

import com.google.gson.Gson;
import com.leomara.delivery.doce_sabor.dto.ClienteDTO;
import com.leomara.delivery.doce_sabor.resources.config.ConfigurationResourceTests;
import static com.leomara.delivery.doce_sabor.until.variables.ClienteVariables.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;


public class ClienteResourceTest extends ConfigurationResourceTests {


    private ClienteDTO cliente;
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        cliente = new ClienteDTO();

        cliente.setNome(NOME_CLIENTE);
        cliente.setCpf(CPF_CLIENTE);
        cliente.setEmail(EMAIL_CLIENTE);
        cliente.setSenha(SENHA_CLIENTE);
        cliente.getTelefones().addAll(Arrays.asList(TELEFONE_CLIENTE));
        cliente.setLogradouro(LOGRADOURO);
        cliente.setBairro(BAIRRO);
        cliente.setNumero(NUMERO);
        cliente.setUf(UF);
        cliente.setCep(CEP);
        cliente.setCidade(CIDADE);
    }

    /** Método insert */

    @Test
    public void deve_salvar_um_cliente_com_sucesso() {
        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(cliente))
        .when()
                .post(URN_CLIENTE)
        .then()
                .log().headers()
            .and()
                .log().body()
            .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:"+ porta +URN_CLIENTE + ID_NOVO_CLIENTE))
                .body("id", equalTo(ID_NOVO_CLIENTE),
                        "nome", equalTo(NOME_CLIENTE),
                        "cpf", equalTo(CPF_CLIENTE),
                        "senha", equalTo(SENHA_CLIENTE),
                        "email", equalTo(EMAIL_CLIENTE),
                        "telefones", contains(TELEFONE_CLIENTE),
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
                .post(URN_CLIENTE)
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
    public void deve_retornar_excecao_ao_salvar_cliente_sem_logradouro_e_outros_campos_do_endereco() {
        ClienteDTO clienteAux = new ClienteDTO();
        clienteAux.setNome(NOME_CLIENTE);
        clienteAux.setCpf(CPF_CLIENTE);
        clienteAux.setEmail(EMAIL_CLIENTE);
        clienteAux.setSenha(SENHA_CLIENTE);
        clienteAux.getTelefones().addAll(Arrays.asList(TELEFONE_CLIENTE));

        given()
                .request()
                .contentType(ContentType.JSON)
                .body(gson.toJson(clienteAux))
        .when()
                .post(URN_CLIENTE)
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
                .post(URN_CLIENTE)
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
                .post(URN_CLIENTE)
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
                .post(URN_CLIENTE)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(HttpStatus.BAD_REQUEST.value()),
                        "message", equalTo(ERRO_EMAIL_CADASTRADO));
    }
}
