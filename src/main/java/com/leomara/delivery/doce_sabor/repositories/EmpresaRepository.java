package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    Optional<Empresa> findByNomeFantasia(String nomeFantasia);

    Optional<Empresa> findByCNPJ(String cnpj);

    Optional<Empresa> findByEmail(String email);
}
