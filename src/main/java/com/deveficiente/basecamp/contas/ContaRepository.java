package com.deveficiente.basecamp.contas;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	Optional<Conta> findByIdOwnerPrimaria(UUID idOwnerPrimario);

    Optional<Conta> findByIdGlobal(UUID idGlobal);
}
