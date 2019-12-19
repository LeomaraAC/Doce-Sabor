package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.domain.Produto;
import com.leomara.delivery.doce_sabor.repositories.CategoriaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.Optional;

import static com.leomara.delivery.doce_sabor.until.variables.CategoriaVariables.*;
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

    private static final String FILTER = "x";

    @BeforeEach
    public void setUp() {
        categoria = new Categoria(ID_EXISTENTE, NOME);
        categoriaAux = new Categoria(ID_EXISTENTE, NOME);

        p1 = new Produto();
        p1.setNome(PRODUTO_1);

        p2 = new Produto();
        p2.setNome(PRODUTO_2);

        page = new PageImpl<>(Arrays.asList(categoria, categoriaAux));

        when(repository.findById(ID_EXISTENTE)).thenReturn(Optional.of(categoria));
        when(repository.save(categoria)).thenReturn(categoriaAux);
        when(repository.filter(" ", PAGE_REQUEST)).thenReturn(page);
    }

    /** Método Insert*/

    @Test
    public void deve_chamar_metodo_save_do_repositorio_ao_inserir() {
        sut.insert(categoria);
        verify(repository).save(categoria);
    }

    @Test
    public void nao_deve_salvar_duas_categorias_com_o_mesmo_nome() {
        when(repository.findByNome(NOME_EXISTENTE)).thenReturn(Optional.of(categoria));
        categoriaAux.setNome(NOME_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.insert(categoriaAux));
        assertEquals(ERRO_CAT_EXISTENTE, exception.getMessage());
    }

    @Test
    public void deve_inserir_categoria_com_sucesso() {
        Categoria cat = sut.insert(categoria);

        verify(repository).save(categoria);
        assertAll("Deve inserir uma categoria com sucesso",
                () -> assertEquals(ID_EXISTENTE, cat.getId()),
                () -> assertEquals(NOME, cat.getNome()));
    }

    /** Método Delete*/

    @Test
    public void nao_deve_deletar_categoria_com_id_inexistente() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.delete(ID_INEXISTENTE));
        assertEquals(ERRO_CAT_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void deve_chamar_metodo_delete_by_id_do_repositorio() {
        sut.delete(ID_EXISTENTE);

        verify(repository).deleteById(ID_EXISTENTE);
    }

    @Test
    public void nao_deve_excluir_categoria_com_produtos_associados() {
        categoria.setProdutos(Arrays.asList(p1, p2));
        exception = assertThrows(DataIntegrityException.class, () -> sut.delete(ID_EXISTENTE));
        assertEquals(ERRO_EXCUIR_CAT_COM_PRODUTO, exception.getMessage());
    }

    /** Método find*/

    @Test
    public void deve_buscar_uma_categoria_pelo_id() {
        Categoria cat = sut.find(ID_EXISTENTE);
        verify(repository).findById(ID_EXISTENTE);

        assertAll("Deve retornar uma categoria",
                    () -> assertEquals(ID_EXISTENTE, cat.getId()),
                    () -> assertEquals(NOME, cat.getNome()));
    }

    @Test
    public void deve_retornar_excecao_ao_nao_encontrar_categoria() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        exception = assertThrows(ObjectNotFoundException.class, () -> sut.find(ID_INEXISTENTE));
        assertEquals(ERRO_CAT_NAO_ENCONTRADA, exception.getMessage());
    }

    /** Método update*/

    @Test
    public void deve_atualizar_categoria_com_sucesso() {
        Categoria cat = sut.update(categoria);

        verify(repository).save(categoria);
        assertAll("Deve atualizar uma categoria com sucesso",
                    () -> assertEquals(ID_EXISTENTE, cat.getId()),
                    () -> assertEquals(NOME, cat.getNome()));
    }

    @Test
    public void nao_deve_atualizar_categoria_id_inexistente() {
        when(repository.findById(ID_INEXISTENTE)).thenReturn(Optional.empty());
        categoria.setId(ID_INEXISTENTE);
        exception = assertThrows(ObjectNotFoundException.class, () ->sut.update(categoria));
        assertEquals(ERRO_CAT_NAO_ENCONTRADA, exception.getMessage());
    }

    @Test
    public void nao_deve_atualizar_categoria_com_nome_igual_a_de_outra_categoria() {
        categoriaAux.setId(ID_EXISTENTE + 1);
        when(repository.findByNome(NOME_EXISTENTE)).thenReturn(Optional.of(categoriaAux));
        categoria.setNome(NOME_EXISTENTE);
        exception = assertThrows(DataIntegrityException.class, () -> sut.update(categoria));
        assertEquals(ERRO_CAT_EXISTENTE, exception.getMessage());
    }

    /** Método findPage com filtro*/

    @Test
    public void deve_chamar_metodo_filter_do_repositorio() {
        sut.findPage(PAGE, LINES_PER_PAGE, ORDER_BY, DIRECTION, "");
        verify(repository).filter("", PAGE_REQUEST);
    }

    @Test
    public void deve_retornar_objeto_tipo_page_filtrado() {
        categoriaAux = new Categoria(ID_EXISTENTE, NOME + " Aux");
        page = new PageImpl<>(Arrays.asList(categoriaAux));
        when(repository.filter(FILTER, PAGE_REQUEST)).thenReturn(page);

        Page<Categoria> catPage = sut.findPage(PAGE, LINES_PER_PAGE, ORDER_BY, DIRECTION, FILTER);
        verify(repository).filter(FILTER, PAGE_REQUEST);
        Categoria cat = catPage.getContent().get(0);
        assertAll("Deve retornar categoria auxiliar.",
                    () -> assertEquals(page.getContent().size(), catPage.getContent().size()),
                    () -> assertEquals(categoriaAux.getNome(), cat.getNome()),
                    () -> assertEquals(categoriaAux.getId(), cat.getId()));
    }
}
