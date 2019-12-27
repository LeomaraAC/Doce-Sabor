package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    @Query("SELECT e FROM Empresa e WHERE nome_fantasia = :nome")
    Optional<Empresa> findByNomeFantasia(@Param("nome") String nomeFantasia);

    Optional<Empresa> findByCnpj (String cnpj);

    Optional<Empresa> findByEmail(String email);
}
