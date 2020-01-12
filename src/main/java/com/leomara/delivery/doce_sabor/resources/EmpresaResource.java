package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.services.EmpresaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {
    @Autowired
    EmpresaService service;

    @ApiOperation(value = "Cadastra uma empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Empresa cadastrada"),
            @ApiResponse(code = 400, message = "Erro de validação")
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Empresa> insert(@Valid @RequestBody Empresa empresa) {
        empresa = service.insert(empresa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }

    @ApiOperation(value = "Ataualiza uma empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Empresa atualizada"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 404, message = "Empresa não encontrada")
    })
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Empresa> update(@PathVariable Integer id, @Valid @RequestBody Empresa empresa) {
        empresa.setId(id);
        empresa = service.update(empresa);
        return ResponseEntity.ok(empresa);
    }

    @ApiOperation(value = "Exclui uma empresa")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Empresa excluida"),
            @ApiResponse(code = 404, message = "Empresa não encontrada")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
