package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.repositories.EmpresaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.leomara.delivery.doce_sabor.until.Variables.ERRO_ID_NECESSARIO;
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
    private Empresa empresaAux;
    private Exception exception;

    @BeforeEach
    void setUp() {
        empresa = new Empresa(ID_NOVO, NOME_FANTASIA, CNPJ, EMAIL, SENHA);
        empresaAux = new Empresa(ID_EXISTENTE, NOME_FANTASIA, CNPJ_EXISTENTE, EMAIL_EXISTENTE, SENHA);

        when(repo.save(empresa)).thenReturn(empresa);
        when(repo.findByNomeFantasia(NOME_FANTASIA_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findByEmail(EMAIL_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findByCnpj(CNPJ_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findById(ID_EXISTENTE)).thenReturn(Optional.of(empresa));
        when(repo.findById(ID_NOVO)).thenReturn(Optional.of(empresa));
        when(repo.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        when(repo.findById(null)).thenThrow(IllegalArgumentException.class);
    }

    /** Inserindo */
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

    /**Apagar */
    @Test
    public void deve_retornar_excessao_ao_apagar_com_id_inexistente() {
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.delete(ID_INEXISTENTE));
        assertEquals(MSG_ERRO_EMPRESA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deve_apagar_uma_empresa_com_sucesso() {
        sut.delete(ID_EXISTENTE);
        verify(repo).deleteById(ID_EXISTENTE);
    }

    /** Atualizar */
    @Test
    public void deve_retornar_excessao_ao_atualizar_empresa_com_nome_fantasia_ja_existente () {
        when(repo.findByNomeFantasia(NOME_FANTASIA_EXISTENTE)).thenReturn(Optional.of(empresaAux));

        empresa.setNome_fantasia(NOME_FANTASIA_EXISTENTE);

        exception = assertThrows(DataIntegrityException.class, () -> sut.update(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_NOME_FANTASIA, exception.getMessage());
    }

    @Test
    public void deve_retornar_excessao_ao_atualizar_empresa_com_id_inexistente() {
        empresa.setId(ID_INEXISTENTE);

        exception = assertThrows(ObjectNotFoundException.class, () -> sut.update(empresa));
        assertEquals(MSG_ERRO_EMPRESA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deve_retornar_excessao_ao_atualizar_empresa_com_CNPJ_ja_existente() {
        when(repo.findByCnpj(CNPJ_EXISTENTE)).thenReturn(Optional.of(empresaAux));

        empresa.setCnpj(CNPJ_EXISTENTE);

        exception = assertThrows(DataIntegrityException.class, () -> sut.update(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_CNPJ, exception.getMessage());

    }

    @Test
    public void deve_retornar_excessao_ao_atualizar_empresa_com_email_ja_existente() {
        when(repo.findByEmail(EMAIL_EXISTENTE)).thenReturn(Optional.of(empresaAux));

        empresa.setEmail(EMAIL_EXISTENTE);

        exception = assertThrows(DataIntegrityException.class, () -> sut.update(empresa));
        assertEquals(MSG_ERRO_DUPLICIDADE_EMAIL, exception.getMessage());
    }

    @Test
    public void deve_atualizar_empresa_com_sucesso() {
        empresaAux = sut.update(empresa);
        verify(repo).save(empresa);
        assertTrue(empresaAux.equals(empresa));
    }

    /** buscando por id */
    @Test
    public void deve_retornar_excessao_ao_buscar_com_id_nulo() {
        exception = assertThrows(DataIntegrityException.class, () -> sut.find(null));
        assertEquals(ERRO_ID_NECESSARIO, exception.getMessage());
    }

    @Test
    public void deve_retornar_excessao_ao_buscar_empresa_com_id_inexistente() {
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.find(ID_INEXISTENTE));
        assertEquals(MSG_ERRO_EMPRESA_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deve_buscar_empresa_de_id_3_com_sucesso() {
        empresaAux = sut.find(ID_EXISTENTE);
        verify(repo).findById(ID_EXISTENTE);
        assertEquals(NOME_FANTASIA, empresaAux.getNome_fantasia());
    }
}
