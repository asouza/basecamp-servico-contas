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

	//hashcode e equals para facilitar testabilidade

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((senhaLimpa == null) ? 0 : senhaLimpa.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NovaPessoaUsuarioRequest other = (NovaPessoaUsuarioRequest) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senhaLimpa == null) {
			if (other.senhaLimpa != null)
				return false;
		} else if (!senhaLimpa.equals(other.senhaLimpa))
			return false;
		return true;
	}
	
	

}
