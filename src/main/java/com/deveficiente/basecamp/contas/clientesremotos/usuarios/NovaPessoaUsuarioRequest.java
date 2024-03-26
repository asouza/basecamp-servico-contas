package com.deveficiente.basecamp.contas.clientesremotos.usuarios;

import org.hibernate.validator.constraints.Length;

import com.deveficiente.basecamp.contas.NovaContaOwnerRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe para ser copiada em qualquer lugar que queira cadastrar uma
 * nova pessoa usuaria remotamente
 * @author albertoluizsouza
 *
 */
public class NovaPessoaUsuarioRequest {

	@NotBlank
	public final String nome;
	@NotBlank
	@Email
	public final String email;
	@NotBlank
	@Length(min = 6)
	public final String senhaLimpa;


	public NovaPessoaUsuarioRequest(
			NovaContaOwnerRequest novaContaOwnerRequest) {
		
		this.nome = novaContaOwnerRequest.getNome();
		this.email = novaContaOwnerRequest.getEmail();
		this.senhaLimpa = novaContaOwnerRequest.getSenha();
	}

}
