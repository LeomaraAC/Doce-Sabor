package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.config.ConfigurationTests;
import com.leomara.delivery.doce_sabor.domain.Categoria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@SpringBootTest
public class CategoriaRepositoryTest extends ConfigurationTests {
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
