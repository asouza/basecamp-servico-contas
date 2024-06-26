package com.deveficiente.basecamp.contas;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class NovaContaRequest {

	@Valid
	private NovaContaOwnerRequest novaContaOwnerRequest;
	@NotBlank
	private String nome;

	public NovaContaRequest(NovaContaOwnerRequest pessoaUsuariaRequest,
			String nome) {
		super();
		this.novaContaOwnerRequest = pessoaUsuariaRequest;
		this.nome = nome;
	}
	
	public NovaContaOwnerRequest getNovaContaOwnerRequest() {
		return novaContaOwnerRequest;
	}

	public Conta toModel(UUID idGlobalOwner) {
		return new Conta(this.nome,idGlobalOwner);
	}

}
