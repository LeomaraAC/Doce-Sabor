package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.repositories.CategoriaRepository;
import com.leomara.delivery.doce_sabor.services.exception.CategoriaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application-test.properties")
public class CategoriaServiceTest {

    @MockBean
    private CategoriaRepository repository;

    @Autowired
    private CategoriaService sut;

    private Exception exception;

    private Categoria categoria;

    private static final String NOME_CAT = "Categoria 1";
    private static final int CAT_ID = 1;


    @BeforeEach
    public void setUp() {
        categoria = new Categoria(CAT_ID, NOME_CAT);
    }

    @Test
    public void deve_chamar_metodo_save_do_repositorio_ao_inserir() {
        sut.insert(categoria);
        verify(repository).save(categoria);
    }

    @Test
    public void nao_deve_salvar_duas_categorias_com_o_mesmo_nome() {
        when(repository.findByNome(NOME_CAT)).thenReturn(Optional.of(categoria));
        exception = assertThrows(CategoriaException.class, () -> sut.insert(categoria));
        assertEquals("A categoria " + NOME_CAT + " já esta cadastrada.", exception.getMessage());
    }
}
