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
        Optional<Categoria> obj = repo.findByNome(categoria.getNome());
        if (obj.isPresent())
            throw new CategoriaException("A categoria " + categoria.getNome() + " já esta cadastrada.");
        repo.save(categoria);
    }
}
