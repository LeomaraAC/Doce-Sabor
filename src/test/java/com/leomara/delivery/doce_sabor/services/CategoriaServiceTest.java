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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
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
    private Produto p1;
    private Produto p2;

    private static final String CAT_NOME = "Categoria 1";
    private static final int CAT_ID = 1;
    private static final String PRODUTO_1 = "Produto 1";
    private static final String PRODUTO_2 = "Produto 2";


    @BeforeEach
    public void setUp() {
        categoria = new Categoria(CAT_ID, CAT_NOME);

        p1 = new Produto();
        p1.setNome(PRODUTO_1);

        p2 = new Produto();
        p2.setNome(PRODUTO_2);

        when(repository.findById(CAT_ID)).thenReturn(Optional.of(categoria));
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
        assertEquals("A categoria " + CAT_NOME + " já esta cadastrada.", exception.getMessage());
    }

    /** Método Delete*/

    @Test
    public void nao_deve_deletar_categoria_com_id_inexistente() {
        when(repository.findById(CAT_ID)).thenReturn(Optional.empty());
        exception = assertThrows(CategoriaException.class, () -> sut.delete(CAT_ID));
        assertEquals("Categoria não encontrada. ID: " + CAT_ID, exception.getMessage());
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

}
