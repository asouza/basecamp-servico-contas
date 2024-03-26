package com.deveficiente.basecamp.contas;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deveficiente.basecamp.contas.clientesremotos.usuarios.NovaPessoaUsuarioRequest;
import com.deveficiente.basecamp.contas.clientesremotos.usuarios.PessoaUsuariaClient;

import jakarta.validation.Valid;

@RestController
public class NovaContaController {

	private PessoaUsuariaClient pessoaUsuariaClient;

	public NovaContaController(PessoaUsuariaClient pessoaUsuariaClient) {
		super();
		this.pessoaUsuariaClient = pessoaUsuariaClient;
	}

	@PostMapping("/api/contas/v1")
	public UUID executa(@Valid @RequestBody NovaContaRequest request) {

		UUID idGlobalOwner = pessoaUsuariaClient
				.criaPessoaUsuaria(new NovaPessoaUsuarioRequest(
						request.getNovaContaOwnerRequest()));

		return idGlobalOwner;
	}
}
