package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.repositories.EnderecoRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository repoEnd;

    public Cliente find(Integer id) {
        if (id == null)
            throw new DataIntegrityException("Necessário um id para buscar.");
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new  ObjectNotFoundException("Cliente não encontrado. ID: "+ id));
    }

    @Transactional
    public Cliente insert(Cliente cliente) {
        verificarEnderecoETelefoneVazio(cliente);

        if (repo.findByCpf(cliente.getCpf()).isPresent())
            throw new DataIntegrityException("O CPF " + cliente.getCpf() + " já esta cadastrado.");

        if (repo.findByEmail(cliente.getEmail()).isPresent())
            throw new DataIntegrityException("O email " + cliente.getEmail() + " já esta cadastrado.");

        cliente = repo.save(cliente);
        repoEnd.save(cliente.getEndereco());
        return cliente;
    }

    private void verificarEnderecoETelefoneVazio(Cliente cliente) {
        if (cliente.getEndereco() == null)
            throw new DataIntegrityException("É obrigatório informar um endereço.");

        if (cliente.getTelefones().isEmpty())
            throw new DataIntegrityException("É obrigatório informar pelo menos um telefone.");
    }


    public Cliente update(Cliente cliente) {
        verificarEnderecoETelefoneVazio(cliente);
        find(cliente.getId());
        Optional<Cliente> objCPF = repo.findByCpf(cliente.getCpf());
        Optional<Cliente> objEmail = repo.findByEmail(cliente.getEmail());
        if (objCPF.isPresent()) {
            if(!objCPF.get().equals(cliente))
                throw new DataIntegrityException("O CPF " + cliente.getCpf() + " já esta cadastrado.");
        }

        if (objEmail.isPresent()) {
            if(!objEmail.get().equals(cliente))
                throw new DataIntegrityException("O email " + cliente.getEmail() + " já esta cadastrado.");
        }
        repoEnd.save(cliente.getEndereco());
        return repo.save(cliente);
    }
}
