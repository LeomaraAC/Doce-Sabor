package com.leomara.delivery.doce_sabor.resources;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.dto.ClienteDTO;
import com.leomara.delivery.doce_sabor.services.ClienteService;
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
@RequestMapping(value = "/clientes")
public class ClienteResources {

    @Autowired
    ClienteService service;

    @ApiOperation(value = "Retorna uma lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna a lista paginada de clientes")
    })
    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<Cliente>> findAll(@RequestParam(value = "filterNome", defaultValue = "") String filterNome,
                                                 @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(value = "linesPerPage", defaultValue = "25") Integer linesPerPage,
                                                 @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                 @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> clientes = service.findPage(page,linesPerPage,orderBy, direction, filterNome);
        return ResponseEntity.ok(clientes);
    }

    @ApiOperation(value = "Retorna um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retorna um cliente"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente cliente = service.find(id);
        return ResponseEntity.ok(cliente);
    }

    @ApiOperation(value = "Cadastra um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente cadastrado"),
            @ApiResponse(code = 400, message = "Erro de validação"),
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<Cliente> insert(@Valid @RequestBody ClienteDTO objDTO) {
        Cliente cliente = service.fromDTO(objDTO);
        cliente = service.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(cliente);
    }

    @ApiOperation(value = "Atualiza um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente atualizado"),
            @ApiResponse(code = 400, message = "Erro de validação"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
    })
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
        Cliente cliente = service.fromDTO(objDTO);
        cliente.setId(id);
        cliente = service.update(cliente);
        return ResponseEntity.ok(cliente);
    }

    @ApiOperation(value = "Exclui um cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cliente excluido"),
            @ApiResponse(code = 404, message = "Cliente não encontrado"),
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
