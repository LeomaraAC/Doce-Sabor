package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.domain.Endereco;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.repositories.EnderecoRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import static com.leomara.delivery.doce_sabor.until.variables.ClienteVariables.*;
import org.junit.jupiter.api.BeforeEach;
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
        endereco = new Endereco(null, LOGRADOURO, NUMERO, BAIRRO, COMPLEMENTO, CEP, CIDADE, UF, clienteAux);

        cliente = new Cliente(ID_EXISTENTE, NOME, CPF, EMAIL, SENHA, endereco);
        cliente.setEndereco(endereco);
        cliente.getTelefones().addAll(Arrays.asList(TEL1, TEL2));

        clienteAux = new Cliente(ID_EXISTENTE, NOME, CPF_EXISTENTE, EMAIL_EXISTENTE, SENHA, null);

        when(repository.findById(ID_EXISTENTE)).thenReturn(Optional.of(cliente));
        when(repository.findById(ID_EXISTENTE + 1)).thenReturn(Optional.of(cliente));
        when(repository.save(cliente)).thenReturn(cliente);
        when(repository.findByCpf(CPF)).thenReturn(Optional.empty());
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.empty());
    }

    /** Método find */
    @Test
     public void deve_retornar_um_cliente_pelo_id() {
        Cliente cl = sut.find(ID_EXISTENTE);
        verify(repository).findById(ID_EXISTENTE);
        assertAll("Deve retornar um cliente",
                    () -> assertEquals(NOME, cl.getNome()),
                () -> assertEquals(ID_EXISTENTE, cl.getId()),
                () -> assertEquals(CPF, cl.getCpf()),
                () -> assertEquals(EMAIL, cl.getEmail()),
                () -> assertEquals(SENHA, cl.getSenha()));
    }

    @Test
    public void deve_retornar_excecao_ao_nao_encontrar_id() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.find(ID_INEXISTENTE));
        assertEquals(ERRO_CLIENTE_NAO_ENCONTRADO, exception.getMessage());
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
        assertAll("Deve inserir um cliente com sucesso",
                    () -> assertEquals(CPF, cl.getCpf()),
                    () -> assertEquals(EMAIL, cl.getEmail()),
                    () -> assertThat(cl.getTelefones(), containsInAnyOrder(TEL1,TEL2)),
                    () -> assertEquals(LOGRADOURO, cl.getEndereco().getLogradouro()),
                () -> assertEquals(NUMERO, cl.getEndereco().getNumero()));

    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_com_o_mesmo_cpf() {
        when(repository.findByCpf(CPF_EXISTENTE)).thenReturn(Optional.of(clienteAux));
        cliente.setCpf(CPF_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(cliente));
        assertEquals(ERRO_CPF_CADASTRADO, exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_inserir_cliente_com_o_mesmo_email() {
        when(repository.findByEmail(EMAIL_EXISTENTE)).thenReturn(Optional.of(clienteAux));
        cliente.setEmail(EMAIL_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(cliente));
        assertEquals(ERRO_EMAIL_CADASTRADO, exception.getMessage());
    }

    /** Método update */
    @Test
    public void deve_atualizar_com_sucesso() {
        Cliente cl = sut.update(cliente);
        verify(repository).save(cliente);
        assertAll("Deve inserir um cliente com sucesso",
                () -> assertEquals(ID_EXISTENTE, cl.getId()),
                () -> assertEquals(CPF, cl.getCpf()),
                () -> assertThat(cl.getTelefones(), containsInAnyOrder(TEL1,TEL2)));
    }
    @Test
    public void deve_retornar_excecao_ao_tentar_atualizar_com_id_inexistente() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        cliente.setId(ID_INEXISTENTE);
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.update(cliente));
        assertEquals(ERRO_CLIENTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    public void  deve_retornar_excecao_ao_atualizar_com_cpf_ja_existente() {
        when(repository.findByCpf(CPF_EXISTENTE)).thenReturn(Optional.of(clienteAux));
        cliente.setId(ID_EXISTENTE + 1);
        cliente.setCpf(CPF_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(cliente));
        assertEquals(ERRO_CPF_CADASTRADO, exception.getMessage());
    }

    @Test
    public void deve_retornar_excecao_ao_atualizar_com_email_ja_existente() {
        when(repository.findByEmail(EMAIL_EXISTENTE)).thenReturn(Optional.of(clienteAux));
        cliente.setId(ID_EXISTENTE + 1);
        cliente.setEmail(EMAIL_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(cliente));
        assertEquals(ERRO_EMAIL_CADASTRADO, exception.getMessage());
    }

    /** Método delete */
    @Test
    public void deve_retornar_excecao_ao_tentar_deletar_com_id_inexistente() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.delete(ID_INEXISTENTE));
        assertEquals(ERRO_CLIENTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    public void deve_chamar_o_metodo_delete_do_repositorio() {
        sut.delete(ID_EXISTENTE);
        verify(repository).deleteById(ID_EXISTENTE);
    }
}
