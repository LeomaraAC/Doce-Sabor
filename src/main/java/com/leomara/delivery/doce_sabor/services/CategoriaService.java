package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.dto.CategoriaDTO;
import com.leomara.delivery.doce_sabor.repositories.CategoriaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository repo;

    public Categoria insert(Categoria categoria) {
        categoria.setId(null);
        Optional<Categoria> obj = repo.findByNome(categoria.getNome());
        if (obj.isPresent())
            throw new DataIntegrityException("A categoria " + categoria.getNome() + " já esta cadastrada.");
        return repo.save(categoria);
    }

    public void delete(int catId) {
        Categoria cat = find(catId);
        if (cat.getProdutos().size() > 0)
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos.");
        repo.deleteById(catId);

    }

    public Categoria find(int catId) {
        Optional<Categoria> obj = repo.findById(catId);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada. ID: " + catId));
    }

    public Categoria update(Categoria categoria) {
        find(categoria.getId());
        Optional<Categoria> obj = repo.findByNome(categoria.getNome());
        if (obj.isPresent()){
            if (!obj.get().equals(categoria))
                throw new DataIntegrityException("A categoria " + categoria.getNome() + " já esta cadastrada.");
        }
        return repo.save(categoria);
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String filterNome) {
        PageRequest request = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.filter(filterNome, request);
    }

    public Categoria fromDTO(CategoriaDTO obj) {
        return new Categoria(obj.getId(), obj.getNome());
    }
}
