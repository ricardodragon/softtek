package com.softtek.repository;

import com.softtek.model.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

    Optional<Cliente> findByNumeroConta(String numeroConta);
}
