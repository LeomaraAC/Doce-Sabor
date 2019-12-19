package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Categoria;
import com.leomara.delivery.doce_sabor.services.CategoriaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @ApiOperation(value = "Retorna uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna uma categoria"),
            @ApiResponse(code = 404, message = "Categoria não encontrada"),
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Categoria> find(@PathVariable Integer id) {
        Categoria obj = service.find(id);
        return ResponseEntity.ok(obj);
    }

    @ApiOperation(value = "Cadastra uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Categoria cadastrada"),
            @ApiResponse(code = 400, message = "Erro de validação"),
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Categoria> insert(@Valid @RequestBody Categoria categoria) {
        categoria = service.insert(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).body(categoria);
    }

    @ApiOperation(value = "Atualiza uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Categoria atualizada"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 404, message = "Categoria não encontrada"),
    })
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Categoria> update (@PathVariable Integer id, @Valid @RequestBody Categoria categoria) {
        categoria.setId(id);
        categoria = service.update(categoria);
        return ResponseEntity.ok(categoria);
    }

    @ApiOperation(value = "Exclui uma categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Categoria excluida"),
            @ApiResponse(code = 404, message = "Categoria não encontrada"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Retornar uma lista de categorias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista paginada de categorias")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<Categoria>> findAll( @RequestParam(value = "nome", defaultValue = "") String nome,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "linesPerPage", defaultValue = "25") Integer linesPerPage,
                                                    @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                    @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Categoria> categorias = service.findPage(page,linesPerPage, orderBy, direction, nome);
        return ResponseEntity.ok(categorias);
    }
}
