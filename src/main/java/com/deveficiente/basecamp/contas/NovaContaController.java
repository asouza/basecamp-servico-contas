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
	private ContaRepository contaRepository;

	public NovaContaController(PessoaUsuariaClient pessoaUsuariaClient,
			RetrierDefault retrierDefault, ContaRepository contaRepository) {
		super();
		this.pessoaUsuariaClient = pessoaUsuariaClient;
		this.retrierDefault = retrierDefault;
		this.contaRepository = contaRepository;
	}

	@PostMapping("/api/contas/v1")
	public UUID executa(@Valid @RequestBody NovaContaRequest request) {

		UUID idGlobalOwner = retrierDefault.executa(() -> pessoaUsuariaClient
				.criaPessoaUsuaria(new NovaPessoaUsuarioRequest(
						request.getNovaContaOwnerRequest())));

		/*
		 * #paraPensar aqui eu estou usando a request de novo passando um
		 * argumento que foi computado em função de dados dessa mesma request.
		 * 
		 * Estou abrindo a porta para inconsistência, pois posso passar um id
		 * global que não tem nada a ver com o retorno de cima
		 */
		Conta novaConta = request.toModel(idGlobalOwner);
		contaRepository.save(novaConta);

		return novaConta.getIdGlobal();
	}
}
