package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClienteServiceTest {
    private static final int ID_CLI = 1;
    private static final String MENSAGEM_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado. ID: " + ID_CLI;
    private static final String NOME_CLI = "Caio Ramos";
    private static final String CPF_CLI = "231.794.256-79";
    private static final String EMAIL_CLI = "caiomarcos@roche.com";
    private static final String SENHA_CLI = "123";
    public static final String MENSAGEM_ID_NULO = "Necessário um id para buscar.";

    @Autowired
    private ClienteService sut;

    @MockBean
    private ClienteRepository repository;

    private Cliente cliente;
    private Exception exception;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(ID_CLI, NOME_CLI, CPF_CLI, EMAIL_CLI, SENHA_CLI);
        when(repository.findById(1)).thenReturn(Optional.of(cliente));
    }

    /** Método find */
    @Test
     public void deve_retornar_um_cliente_pelo_id() {
        Cliente cl = sut.find(ID_CLI);
        verify(repository).findById(ID_CLI);
        assertAll("Deve retornar um cliente",
                    () -> assertEquals(NOME_CLI, cl.getNome()),
                () -> assertEquals(ID_CLI, cl.getId()),
                () -> assertEquals(CPF_CLI, cl.getCpf()),
                () -> assertEquals(EMAIL_CLI, cl.getEmail()),
                () -> assertEquals(SENHA_CLI, cl.getSenha()));
    }
    @Test
    public void deve_excecao_ao_nao_encontrar_id() {
        when(repository.findById(ID_CLI)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.find(ID_CLI));
        assertEquals(MENSAGEM_CLIENTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_receber_id_nulo() {
        when((repository.findById(null))).thenThrow(NullPointerException.class);
        exception = assertThrows(DataIntegrityException.class, () -> sut.find(null));
        assertEquals(MENSAGEM_ID_NULO, exception.getMessage());
    }
}
