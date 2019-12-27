package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.until.ConfigurationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.leomara.delivery.doce_sabor.until.variables.EmpresaVariables.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EmpresaRepositoryTest extends ConfigurationTests {
    @Autowired
    EmpresaRepository sut;

    private Optional<Empresa> empresa;

    /** findByNomeFantasia */
    @Test
    public void deve_retornar_empresa_pelo_nome_fantasia_Sabor_Caseiro() {
        empresa = sut.findByNomeFantasia(NOME_FANTASIA_EXISTENTE);

        assertAll("Deve retornar a empresa Doce Sabor",
                () -> assertTrue(empresa.isPresent()),
                () -> assertEquals(NOME_FANTASIA_EXISTENTE, empresa.get().getNome_fantasia()),
                () -> assertEquals(EMAIL_EXISTENTE, empresa.get().getEmail()),
                () -> assertEquals(CNPJ_EXISTENTE, empresa.get().getCnpj()));
    }

    @Test
    public void deve_retornar_optionar_vazio_para_o_nome_fantasia_RedRock() {
        empresa = sut.findByNomeFantasia(NOME_FANTASIA);
        assertFalse(empresa.isPresent());
    }

    /** findByCnpj */
    @Test
    public void deve_retornar_empresa_com_cnpj_36112464000137() {
        empresa = sut.findByCnpj(CNPJ_EXISTENTE);
        assertAll("Deve retornar a empresa Doce Sabor",
                () -> assertTrue(empresa.isPresent()),
                () -> assertEquals(NOME_FANTASIA_EXISTENTE, empresa.get().getNome_fantasia()),
                () -> assertEquals(EMAIL_EXISTENTE, empresa.get().getEmail()),
                () -> assertEquals(CNPJ_EXISTENTE, empresa.get().getCnpj()));
    }

    @Test
    public void deve_retornar_optionar_vazio_para_o_cnpj_19712232000109() {
        empresa = sut.findByCnpj(CNPJ);
        assertFalse(empresa.isPresent());
    }

    /** findByEmail */
    @Test
    public void deve_retornar_empresa_com_email_da_empresa_sabor_caseiro() {
        empresa = sut.findByEmail(EMAIL_EXISTENTE);
        assertAll("Deve retornar a empresa Doce Sabor",
                () -> assertTrue(empresa.isPresent()),
                () -> assertEquals(NOME_FANTASIA_EXISTENTE, empresa.get().getNome_fantasia()),
                () -> assertEquals(EMAIL_EXISTENTE, empresa.get().getEmail()),
                () -> assertEquals(CNPJ_EXISTENTE, empresa.get().getCnpj()));
    }

    @Test
    public void deve_retornar_optionar_vazio_para_o_email_inexistente() {
        empresa = sut.findByEmail(EMAIL);
        assertFalse(empresa.isPresent());
    }
}
