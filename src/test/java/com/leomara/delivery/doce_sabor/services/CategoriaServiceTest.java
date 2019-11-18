package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.domain.Produto;
import com.leomara.delivery.doce_sabor.repositories.CategoriaRepository;
import com.leomara.delivery.doce_sabor.services.exception.CategoriaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CategoriaServiceTest {

    @MockBean
    private CategoriaRepository repository;

    @Autowired
    private CategoriaService sut;

    private Exception exception;

    private Categoria categoria;
    private Categoria categoriaAux;
    private Produto p1;
    private Produto p2;
    private Page<Categoria> page;

    private static final String CAT_NOME = "Categoria 1";
    private static final int CAT_ID = 1;
    private static final String PRODUTO_1 = "Produto 1";
    private static final String PRODUTO_2 = "Produto 2";
    private static final String MENSAGEM_CATEGORIA_NÃO_ENCONTRADA = "Categoria não encontrada. ID: " + CAT_ID;
    private static final String MENSAGEM_CATEGORIA_JA_EXISTENTE = "A categoria " + CAT_NOME + " já esta cadastrada.";

    //Paginação
    private static final Integer PAGE = 0;
    private static final Integer LINES_PER_PAGE = 10;
    private static final String ORDER_BY = "nome";
    private static final String  DIRECTION = "ASC";
    private static final PageRequest PAGE_REQUEST = PageRequest.of(PAGE, LINES_PER_PAGE,
                                                                    Sort.Direction.valueOf(DIRECTION), ORDER_BY);

    @BeforeEach
    public void setUp() {
        categoria = new Categoria(CAT_ID, CAT_NOME);
        categoriaAux = new Categoria(CAT_ID, CAT_NOME);

        p1 = new Produto();
        p1.setNome(PRODUTO_1);

        p2 = new Produto();
        p2.setNome(PRODUTO_2);

        page = new PageImpl<>(Arrays.asList(categoria, categoriaAux));

        when(repository.findById(CAT_ID)).thenReturn(Optional.of(categoria));
        when(repository.save(categoria)).thenReturn(categoriaAux);
        when(repository.findAll(PAGE_REQUEST)).thenReturn(page);
    }

    /** Método Insert*/

    @Test
    public void deve_chamar_metodo_save_do_repositorio_ao_inserir() {
        sut.insert(categoria);
        verify(repository).save(categoria);
    }

    @Test
    public void nao_deve_salvar_duas_categorias_com_o_mesmo_nome() {
        when(repository.findByNome(CAT_NOME)).thenReturn(Optional.of(categoria));
        exception = assertThrows(CategoriaException.class, () -> sut.insert(categoria));
        assertEquals(MENSAGEM_CATEGORIA_JA_EXISTENTE, exception.getMessage());
    }

    @Test
    public void deve_inserir_categoria_com_sucesso() {
        categoria.setId(null);
        Categoria cat = sut.insert(categoria);

        verify(repository).save(categoria);
        assertAll("Deve inserir uma categoria com sucesso",
                () -> assertEquals(CAT_ID, cat.getId()),
                () -> assertEquals(CAT_NOME, cat.getNome()));
    }

    /** Método Delete*/

    @Test
    public void nao_deve_deletar_categoria_com_id_inexistente() {
        when(repository.findById(CAT_ID)).thenReturn(Optional.empty());
        exception = assertThrows(CategoriaException.class, () -> sut.delete(CAT_ID));
        assertEquals(MENSAGEM_CATEGORIA_NÃO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deve_chamar_metodo_delete_by_id_do_repositorio() {
        sut.delete(CAT_ID);

        verify(repository).deleteById(CAT_ID);
    }

    @Test
    public void nao_deve_excluir_categoria_com_produtos_associados() {
        categoria.setProdutos(Arrays.asList(p1, p2));
        exception = assertThrows(CategoriaException.class, () -> sut.delete(CAT_ID));
        assertEquals("Não é possível excluir uma categoria que possui produtos.", exception.getMessage());
    }

    /** Método find*/

    @Test
    public void deve_buscar_uma_categoria_pelo_id() {
        Categoria cat = sut.find(CAT_ID);
        verify(repository).findById(CAT_ID);

        assertAll("Deve retornar uma categoria",
                    () -> assertEquals(CAT_ID, cat.getId()),
                    () -> assertEquals(CAT_NOME, cat.getNome()));
    }

    @Test
    public void deve_retornar_excecao_ao_nao_encontrar_categoria() {
        when(repository.findById(CAT_ID)).thenReturn(Optional.empty());
        exception = assertThrows(CategoriaException.class, () -> sut.find(CAT_ID));
        assertEquals(MENSAGEM_CATEGORIA_NÃO_ENCONTRADA, exception.getMessage());
    }

    /** Método update*/

    @Test
    public void deve_atualizar_categoria_com_sucesso() {
        Categoria cat = sut.update(categoria);

        verify(repository).save(categoria);
        assertAll("Deve atualizar uma categoria com sucesso",
                    () -> assertEquals(CAT_ID, cat.getId()),
                    () -> assertEquals(CAT_NOME, cat.getNome()));
    }

    @Test
    public void nao_deve_atualizar_categoria_id_inexistente() {
        when(repository.findById(CAT_ID)).thenReturn(Optional.empty());
        exception = assertThrows(CategoriaException.class, () ->sut.update(categoria));
        assertEquals(MENSAGEM_CATEGORIA_NÃO_ENCONTRADA, exception.getMessage());
    }

    //NÃO PERMITIR ATUALIZAR UMA CATEGORIA COM O NOME JÁ EXISTENTE
    @Test
    public void nao_deve_atualizar_categoria_com_nome_igual_a_de_outra_categoria() {
        categoriaAux.setId(CAT_ID + 1);
        when(repository.findByNome(CAT_NOME)).thenReturn(Optional.of(categoriaAux));
        exception = assertThrows(CategoriaException.class, () -> sut.update(categoria));
        assertEquals(MENSAGEM_CATEGORIA_JA_EXISTENTE, exception.getMessage());
    }

    /** Método findPage*/

    @Test
    public void deve_chamar_metodo_find_all_do_repositorio() {
        sut.findPage(PAGE, LINES_PER_PAGE, ORDER_BY, DIRECTION);
        verify(repository).findAll(PAGE_REQUEST);
    }

    @Test
    public void deve_retornar_objeto_tipo_page() {
        Page<Categoria> catPage = sut.findPage(PAGE, LINES_PER_PAGE, ORDER_BY, DIRECTION);
        verify(repository).findAll(PAGE_REQUEST);
        assertEquals(page.getContent().size(), catPage.getContent().size());
    }
}
