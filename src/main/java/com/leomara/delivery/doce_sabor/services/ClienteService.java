package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {
        if (id == null)
            throw new DataIntegrityException("Necessário um id para buscar.");
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new  ObjectNotFoundException("Cliente não encontrado. ID: "+ id));
    }
}
