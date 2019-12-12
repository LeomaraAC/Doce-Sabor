package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.until.ConfigurationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.leomara.delivery.doce_sabor.until.variables.ClienteVariables.*;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteRepositoryTest extends ConfigurationTests {
    @Autowired
    ClienteRepository sut;

    private Page<Cliente> clientes;
    List<String> CPFs = new ArrayList<>();

    /** Método filter */
    @Test
    public void deve_retornar_todos_clientes_paginados() {
        clientes = sut.filter("", PAGE_REQUEST);
        clientes.getContent().forEach( cliente ->  this.CPFs.add(cliente.getCpf()));
        assertAll("Deve retornar todos os clientes",
                () -> assertEquals(CPF_TODOS_CLIENTES.size(), clientes.getTotalElements()),
                () -> assertThat(CPFs,containsInAnyOrder(CPF_TODOS_CLIENTES.toArray())));
    }

    @Test
    public void deve_retornar_todos_os_clientes_que_contem_ira_no_nome() {
        clientes = sut.filter(STRING_FILTRO, PAGE_REQUEST);
        clientes.getContent().forEach(cliente -> this.CPFs.add(cliente.getCpf()));

        assertAll("Deve retornar clientes que contêm a string ira",
                () -> assertEquals(CPF_CLIENTES_IRA.size(), clientes.getTotalElements()),
                () -> assertThat(CPFs, containsInAnyOrder(CPF_CLIENTES_IRA.toArray())));
    }

    @Test
    public void deve_retornar_content_vazio_para_filtro_com_nome_abcd() {
        clientes = sut.filter("abcd", PAGE_REQUEST);
        assertTrue(clientes.getContent().isEmpty());
    }


}
