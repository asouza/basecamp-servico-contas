package com.deveficiente.basecamp.contas;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class NovaContaOwnerRequest {

	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@Length(min = 6)
	private String senha;

	public NovaContaOwnerRequest(@NotBlank String nome,
			@NotBlank @Email String email, @Length(min = 6) String senha) {
		super();
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getSenha() {
		return senha;
	}

}
