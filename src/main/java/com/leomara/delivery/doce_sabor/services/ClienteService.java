package com.leomara.delivery.doce_sabor.services;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import com.leomara.delivery.doce_sabor.repositories.ClienteRepository;
import com.leomara.delivery.doce_sabor.services.exception.DataIntegrityException;
import com.leomara.delivery.doce_sabor.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Cliente insert(Cliente cliente) {
        cliente.setId(null);
        cliente.getEndereco().setId(null);
        if (repo.findByCpf(cliente.getCpf()).isPresent())
            throw new DataIntegrityException("O CPF " + cliente.getCpf() + " já esta cadastrado.");

        if (repo.findByEmail(cliente.getEmail()).isPresent())
            throw new DataIntegrityException("O email " + cliente.getEmail() + " já esta cadastrado.");

        cliente = repo.save(cliente);
        return cliente;
    }

    public Cliente update(Cliente cliente) {
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
        return repo.save(cliente);
    }

//    public Cliente fromDTO(ClienteDTO objDTO) {
//        Endereco endereco = new Endereco(objDTO.getId(), objDTO.getLogradouro(),objDTO.getNumero(),objDTO.getBairro(),
//                objDTO.getComplemento(), objDTO.getCep(), objDTO.getCidade(), objDTO.getUf(), null);
//
//        Cliente cliente = new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getCpf(), objDTO.getEmail(),
//                objDTO.getSenha(), endereco);
//        cliente.setTelefones(objDTO.getTelefones());
//        endereco.setCliente(cliente);
//
//        return cliente;
//    }

    public void delete(Integer id) {
        find(id);
        repo.deleteById(id);
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String filterNome) {
        PageRequest request = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return repo.filter(filterNome, request);
    }
}
