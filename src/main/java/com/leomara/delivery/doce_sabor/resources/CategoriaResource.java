package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.dto.CategoriaDTO;
import com.leomara.delivery.doce_sabor.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    CategoriaService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = service.find(id);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Categoria> insert(@Valid @RequestBody CategoriaDTO obj) {
        Categoria categoria = service.fromDTO(obj);
        categoria = service.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    @PutMapping
    public ResponseEntity<Categoria> update (@Valid @RequestBody CategoriaDTO obj) {
        Categoria categoria = service.fromDTO(obj);
        categoria = service.update(categoria);
        return ResponseEntity.ok(categoria);
    }
}
