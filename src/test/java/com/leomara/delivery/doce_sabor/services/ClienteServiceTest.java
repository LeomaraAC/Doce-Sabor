package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.domain.Endereco;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.repositories.EnderecoRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@SpringBootTest
public class ClienteServiceTest {
    private static final int ID_CLI = 1;
    private static final String MENSAGEM_CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado. ID: " + ID_CLI;
    private static final String NOME_CLI = "Caio Ramos";
    private static final String CPF_CLI = "231.794.256-79";
    private static final String EMAIL_CLI = "caiomarcos@roche.com";
    private static final String SENHA_CLI = "123";
    private static final String MENSAGEM_ID_NULO = "Necessário um id para buscar.";
    private static final String TEL1 = "(19) 2987-8860";
    private static final String TEL2 = "(19) 98386-7407";

    @Autowired
    private ClienteService sut;

    @MockBean
    private ClienteRepository repository;

    @MockBean
    private EnderecoRepository repositoryEndereco;

    private Cliente cliente;
    private Cliente clienteAux;
    private Endereco endereco;
    private Exception exception;

    @BeforeEach
    void setUp() {
        endereco = new Endereco(null, "Rua Flores", "300", "Jardim", "Apto 203", "38220834", "Uberlândia", "MG", clienteAux);

        cliente = new Cliente(ID_CLI, NOME_CLI, CPF_CLI, EMAIL_CLI, SENHA_CLI, endereco);
        cliente.setEndereco(endereco);
        cliente.getTelefones().addAll(Arrays.asList(TEL1, TEL2));

        clienteAux = new Cliente(ID_CLI, NOME_CLI, CPF_CLI, EMAIL_CLI, SENHA_CLI, null);

        when(repository.findById(ID_CLI)).thenReturn(Optional.of(cliente));
        when(repository.findById(ID_CLI + 1)).thenReturn(Optional.of(cliente));
        when(repository.save(cliente)).thenReturn(cliente);
        when(repository.findByCpf(CPF_CLI)).thenReturn(Optional.empty());
        when(repository.findByEmail(EMAIL_CLI)).thenReturn(Optional.empty());
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
    public void deve_retornar_excecao_ao_nao_encontrar_id() {
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

    /** Método insert */
    @Test
    public void deve_inserir_um_cliente_com_sucesso() {
        cliente.setId(null);
        Cliente cl = sut.insert(cliente);
        verify(repository).save(cliente);
        verify(repositoryEndereco).save(endereco);
        assertAll("Deve inserir um cliente com sucesso",
                    () -> assertEquals(CPF_CLI, cl.getCpf()),
                    () -> assertEquals(EMAIL_CLI, cl.getEmail()),
                    () -> assertThat(cl.getTelefones(), containsInAnyOrder(TEL1,TEL2)),
                    () -> assertEquals("Rua Flores", cl.getEndereco().getLogradouro()),
                () -> assertEquals("300", cl.getEndereco().getNumero()));

    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_sem_endereco() {
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(clienteAux));
        assertEquals("É obrigatório informar um endereço.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_sem_telefone() {
        clienteAux.setEndereco(endereco);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(clienteAux));
        assertEquals("É obrigatório informar pelo menos um telefone.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_com_o_mesmo_cpf() {
        when(repository.findByCpf(CPF_CLI)).thenReturn(Optional.of(cliente));
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(cliente));
        assertEquals("O CPF " + cliente.getCpf() + " já esta cadastrado.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_com_o_mesmo_email() {
        when(repository.findByEmail(EMAIL_CLI)).thenReturn(Optional.of(cliente));
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(cliente));
        assertEquals("O email " + cliente.getEmail() + " já esta cadastrado.", exception.getMessage());
    }

    /** Método update */
    @Test
    public void deve_atualizar_com_sucesso() {
        Cliente cl = sut.update(cliente);
        verify(repository).save(cliente);
        verify(repositoryEndereco).save(endereco);
        assertAll("Deve inserir um cliente com sucesso",
                () -> assertEquals(ID_CLI, cl.getId()),
                () -> assertEquals(CPF_CLI, cl.getCpf()),
                () -> assertThat(cl.getTelefones(), containsInAnyOrder(TEL1,TEL2)));
    }
    @Test
    public void deve_retornar_excecao_ao_tentar_atualizar_com_id_inexistente() {
        when(repository.findById(ID_CLI)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.update(cliente));
        assertEquals("Cliente não encontrado. ID: " + ID_CLI, exception.getMessage());
    }

    @Test
    public void  deve_retornar_excecao_ao_atualizar_com_cpf_ja_existente() {
        when(repository.findByCpf(CPF_CLI)).thenReturn(Optional.of(clienteAux));
        cliente.setId(ID_CLI + 1);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(cliente));
        assertEquals("O CPF " + cliente.getCpf() + " já esta cadastrado.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_atualizar_com_email_ja_existente() {
        when(repository.findByEmail(EMAIL_CLI)).thenReturn(Optional.of(clienteAux));
        cliente.setId(ID_CLI + 1);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(cliente));
        assertEquals("O email " + cliente.getEmail() + " já esta cadastrado.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_tentar_atualizar_sem_endereco() {
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(clienteAux));
        assertEquals("É obrigatório informar um endereço.", exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_tentar_atualizar_sem_telefone() {
        clienteAux.setEndereco(endereco);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(clienteAux));
        assertEquals("É obrigatório informar pelo menos um telefone.", exception.getMessage());
    }
}
