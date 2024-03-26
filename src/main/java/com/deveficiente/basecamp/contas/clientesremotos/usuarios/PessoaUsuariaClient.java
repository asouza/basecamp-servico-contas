package com.deveficiente.basecamp.contas.clientesremotos.usuarios;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 * A ideia aqui é já deixar um código pronto no projeto original que facilita
 * o consumo da api via um cliente remoto. Quem quiser, basta copiar
 * este código e pronto.
 * @author albertoluizsouza
 *
 */
@FeignClient(name = "pessoaUsuariaClient", url = "${servicos.host-usuarios}")
public interface PessoaUsuariaClient {

	@PostMapping("/api/pessoas-usuarias/v1")
	UUID criaPessoaUsuaria(@RequestBody NovaPessoaUsuarioRequest request);
}