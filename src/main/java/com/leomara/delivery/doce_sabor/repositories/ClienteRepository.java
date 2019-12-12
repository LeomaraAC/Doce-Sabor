package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    @Transactional(readOnly = true)
    Optional<Cliente> findByCpf(String cpf);

    @Transactional(readOnly = true)
    Optional<Cliente> findByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT c FROM Cliente c WHERE nome LIKE %:nome%")
    Page<Cliente> filter(@Param("nome") String nome, Pageable pageable);
}
