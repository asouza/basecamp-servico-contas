package com.deveficiente.basecamp.contas;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Conta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private @NotBlank String nome;
	@NotNull
	@Column(unique = true)
	private UUID idOwnerPrimaria;
	@NotNull
	private UUID idGlobal = UUID.randomUUID();
	
	@Deprecated
	public Conta() {
		
	}

	public Conta(@NotBlank String nome, UUID idOwnerPrimaria) {
		this.nome = nome;
		this.idOwnerPrimaria = idOwnerPrimaria;
	}

	
	public UUID getIdGlobal() {
		return idGlobal;
	}
}
