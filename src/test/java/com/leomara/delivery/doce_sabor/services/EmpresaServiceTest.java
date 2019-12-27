package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.repositories.EmpresaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.leomara.delivery.doce_sabor.until.variables.EmpresaVariables.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmpresaServiceTest {
    @Autowired
    private EmpresaService sut;

    @MockBean
    private EmpresaRepository repo;

    private Empresa empresa;
    private Exception exception;

    @BeforeEach
    void setUp() {
        empresa = new Empresa(null, NOME_FANTASIA, CNPJ, EMAIL, SENHA);
        when(repo.save(empresa)).thenReturn(empresa);
        when(repo.findByNomeFantasia(NOME_FANTASIA_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findByEmail(EMAIL_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findByCnpj(CNPJ_EXISTENTE)).thenReturn(Optional.of(empresa));
    }

    @Test
    public void deve_inserir_uma_empresa_com_sucesso() {
        Empresa emp = sut.insert(empresa);
        verify(repo).save(empresa);

        assertAll("Deve salvar uma empresa com sucesso",
                    () -> assertEquals(CNPJ, emp.getCnpj()),
                    () -> assertEquals(NOME_FANTASIA, emp.getNome_fantasia()));
    }

    @Test
    public void deve_retornar_excessao_ao_salvar_empresa_com_nome_fantasia_ja_existente() {
        empresa.setNome_fantasia(NOME_FANTASIA_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_NOME_FANTASIA, exception.getMessage());
    }

    @Test
    public void deve_retornar_excessao_ao_salvar_empresa_com_email_ja_existente() {
        empresa.setEmail(EMAIL_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_EMAIL, exception.getMessage());
    }

    @Test
    public void deve_retornar_excessao_ao_salvar_empresa_com_CNPJ_ja_existente() {
        empresa.setCnpj(CNPJ_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_CNPJ, exception.getMessage());
    }
}