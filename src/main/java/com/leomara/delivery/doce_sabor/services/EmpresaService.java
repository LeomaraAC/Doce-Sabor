package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import com.leomara.delivery.doce_sabor.repositories.EmpresaRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public void delete(Integer id) {
        find(id);
        repo.deleteById(id);
    }

    public Empresa find(Integer id) {
        Optional<Empresa> empresa = repo.findById(id);
        return empresa.orElseThrow(() -> new ObjectNotFoundException("Empresa não encontrada. ID: " + id));
    }

    public Empresa update(Empresa empresa) {
        find(empresa.getId());
        validarUpdate(repo.findByNomeFantasia(empresa.getNome_fantasia()), empresa,
                "O nome fantasia "+empresa.getNome_fantasia()+" já esta cadastrado.");

        validarUpdate(repo.findByCnpj(empresa.getCnpj()), empresa, "O CNPJ "+empresa.getCnpj()+" já esta cadastrado.");

        validarUpdate(repo.findByEmail(empresa.getEmail()), empresa, "O email "+empresa.getEmail()+" já esta cadastrado.");
        return repo.save(empresa);
    }

    private void validarUpdate(Optional<Empresa> obj, Empresa empresa, String msg) {
        if (obj.isPresent())
            if (!obj.get().equals(empresa))
                throw new DataIntegrityException(msg);
    }
}
