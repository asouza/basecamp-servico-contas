package com.deveficiente.basecamp.contas;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deveficiente.basecamp.contas.clientesremotos.usuarios.NovaPessoaUsuarioRequest;
import com.deveficiente.basecamp.contas.clientesremotos.usuarios.PessoaUsuariaClient;
import com.deveficiente.basecamp.contas.compartilhado.spring.RetrierDefault;

import jakarta.validation.Valid;

@RestController
public class NovaContaController {

	private PessoaUsuariaClient pessoaUsuariaClient;
	private RetrierDefault retrierDefault;

	public NovaContaController(PessoaUsuariaClient pessoaUsuariaClient,
			RetrierDefault retrierDefault) {
		super();
		this.pessoaUsuariaClient = pessoaUsuariaClient;
		this.retrierDefault = retrierDefault;
	}

	@PostMapping("/api/contas/v1")
	public UUID executa(@Valid @RequestBody NovaContaRequest request) {


		UUID idGlobalOwner = retrierDefault.executa(() -> 
				pessoaUsuariaClient
				.criaPessoaUsuaria(new NovaPessoaUsuarioRequest(
						request.getNovaContaOwnerRequest())
						)
				);

		return idGlobalOwner;
	}
}
