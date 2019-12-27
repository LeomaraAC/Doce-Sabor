package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.repositories.EmpresaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    EmpresaRepository repo;

    public Empresa insert(Empresa empresa) {
        empresa.setId(null);
        if (repo.findByNomeFantasia(empresa.getNome_fantasia()).isPresent())
            throw new DataIntegrityException("O nome fantasia "+empresa.getNome_fantasia()+" já esta cadastrado.");

        if (repo.findByEmail(empresa.getEmail()).isPresent())
            throw new DataIntegrityException("O email "+empresa.getEmail()+" já esta cadastrado.");

        if (repo.findByCnpj(empresa.getCnpj()).isPresent())
            throw new DataIntegrityException("O CNPJ "+empresa.getCnpj()+" já esta cadastrado.");

        return repo.save(empresa);
    }
}
