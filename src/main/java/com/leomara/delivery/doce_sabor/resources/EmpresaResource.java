package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/empresas")
public class EmpresaResource {
    @Autowired
    EmpresaService service;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Empresa> insert(@Valid @RequestBody Empresa empresa) {
        empresa = service.insert(empresa);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(empresa);
    }
}
