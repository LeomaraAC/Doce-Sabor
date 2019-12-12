package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.until.ConfigurationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.leomara.delivery.doce_sabor.until.variables.ClienteVariables.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteRepositoryTest extends ConfigurationTests {
    @Autowired
    ClienteRepository sut;

    private Page<Cliente> clientePage;
    private Optional<Cliente> cliente;
    List<String> CPFs = new ArrayList<>();

    /** Método filter */
    @Test
    public void deve_retornar_todos_clientes_paginados() {
        clientePage = sut.filter("", PAGE_REQUEST);
        clientePage.getContent().forEach(cliente ->  this.CPFs.add(cliente.getCpf()));
        assertAll("Deve retornar todos os clientes",
                () -> assertEquals(CPF_TODOS_CLIENTES.size(), clientePage.getTotalElements()),
                () -> assertThat(CPFs,containsInAnyOrder(CPF_TODOS_CLIENTES.toArray())));
    }

    @Test
    public void deve_retornar_todos_os_clientes_que_contem_ira_no_nome() {
        clientePage = sut.filter(STRING_FILTRO, PAGE_REQUEST);
        clientePage.getContent().forEach(cliente -> this.CPFs.add(cliente.getCpf()));

        assertAll("Deve retornar clientes que contêm a string ira",
                () -> assertEquals(CPF_CLIENTES_IRA.size(), clientePage.getTotalElements()),
                () -> assertThat(CPFs, containsInAnyOrder(CPF_CLIENTES_IRA.toArray())));
    }

    @Test
    public void deve_retornar_content_vazio_para_filtro_com_nome_abcd() {
        clientePage = sut.filter("abcd", PAGE_REQUEST);
        assertTrue(clientePage.getContent().isEmpty());
    }

    /** Método findByCpf*/
    @Test
    public void deve_retornar_cliente_com_CPF_69811291322() {
        cliente = sut.findByCpf(CPF_EXISTENTE);
        assertAll("Deve retornar a cliente Lúcia",
                () -> assertTrue(cliente.isPresent()),
                () -> assertEquals(CPF_EXISTENTE, cliente.get().getCpf()),
                () -> assertEquals(NOME_EXISTE, cliente.get().getNome()),
                () -> assertEquals(EMAIL_EXISTENTE, cliente.get().getEmail()));
    }

    @Test
    public void deve_retornar_vazio_para_cpf_que_nao_existe() {
        cliente = sut.findByCpf(CPF);
        assertFalse(cliente.isPresent());
    }

    /** Método findByEmail */
    @Test
    public void deve_retornar_cliente_com_email_luciabc() {
        cliente = sut.findByEmail(EMAIL_EXISTENTE);
        assertAll("Deve retornar a cliente Lúcia",
                () -> assertTrue(cliente.isPresent()),
                () -> assertEquals(CPF_EXISTENTE, cliente.get().getCpf()),
                () -> assertEquals(NOME_EXISTE, cliente.get().getNome()),
                () -> assertEquals(EMAIL_EXISTENTE, cliente.get().getEmail()));
    }

    @Test
    public void deve_retornar_vazio_para_email_que_nao_existe() {
        cliente = sut.findByEmail(EMAIL);
        assertFalse(cliente.isPresent());
    }
}
