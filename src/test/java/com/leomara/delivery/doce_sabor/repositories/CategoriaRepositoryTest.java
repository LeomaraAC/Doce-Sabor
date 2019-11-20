package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.*;


import java.util.List;
import java.util.Optional;

@Sql(value = "/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class CategoriaRepositoryTest {
    @Autowired
    private CategoriaRepository sut;
    private Page<Categoria> categorias;

    private static final PageRequest PAGE_REQUEST = PageRequest.of(0, 10,
            Sort.Direction.valueOf("ASC"), "nome");

    @Test
    public void deve_retornar_uma_categoria_pelo_nome() {
        Optional<Categoria> obj = sut.findByNome("Pães");

        assertTrue(obj.isPresent());

        Categoria cat = obj.get();
        assertAll("Deve retornar uma Categoria",
                    () -> assertEquals(3, cat.getId()),
                    () -> assertEquals("Pães", cat.getNome()));
    }

    @Test
    public void nao_deve_retornar_uma_categoria_pelo_nome() {
        Optional<Categoria> obj =sut.findByNome("Festa social");
        assertFalse(obj.isPresent());
    }

    @Test
    public void deve_retornar_as_categorias_filtradas_pelo_nome() {
        categorias = sut.filter("e", PAGE_REQUEST);
        assertEquals(2, categorias.getNumberOfElements());
    }

    @Test
    public void deve_retornar_todas_as_categorias() {
        categorias = sut.filter("", PAGE_REQUEST);
        assertEquals(4, categorias.getNumberOfElements());

    }

    @Test
    public void nao_deve_retornar_nada_filtro_nao_existente() {
        categorias = sut.filter(" xz", PAGE_REQUEST);
        assertEquals(0, categorias.getNumberOfElements());
    }

}
