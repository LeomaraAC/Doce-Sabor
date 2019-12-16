package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.dto.ClienteDTO;
import com.leomara.delivery.doce_sabor.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

    @Autowired
    ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> insert(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente cliente = service.fromDTO(objDTO);
        cliente = service.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente cliente = service.fromDTO(objDTO);
        cliente.setId(id);
        cliente = service.update(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
