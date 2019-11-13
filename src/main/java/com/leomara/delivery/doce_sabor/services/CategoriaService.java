package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.repositories.CategoriaRepository;
import com.leomara.delivery.doce_sabor.services.exception.CategoriaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository repo;

    public void insert(Categoria categoria) {
        categoria.setId(null);
        Optional<Categoria> obj = repo.findByNome(categoria.getNome());
        if (obj.isPresent())
            throw new CategoriaException("A categoria " + categoria.getNome() + " já esta cadastrada.");
        repo.save(categoria);
    }

    public void delete(int catId) {
        Categoria cat = find(catId);
        if (cat.getProdutos().size() > 0)
            throw new CategoriaException("Não é possível excluir uma categoria que possui produtos.");
        repo.deleteById(catId);

    }

    public Categoria find(int catId) {
        Optional<Categoria> obj = repo.findById(catId);
        return obj.orElseThrow(() -> new CategoriaException("Categoria não encontrada. ID: " + catId));
    }

    public Categoria update(Categoria categoria) {
        find(categoria.getId());
        return repo.save(categoria);
    }
}
