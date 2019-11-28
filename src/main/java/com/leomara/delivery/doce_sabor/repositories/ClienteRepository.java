package com.leomara.delivery.doce_sabor.repositories;

import com.leomara.delivery.doce_sabor.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
