package com.deveficiente.basecamp.contas;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import com.deveficiente.basecamp.contas.clientesremotos.usuarios.NovaPessoaUsuarioRequest;
import com.deveficiente.basecamp.contas.clientesremotos.usuarios.PessoaUsuariaClient;
import com.deveficiente.basecamp.contas.compartilhado.CustomMockMvc;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class NovaContaControllerTest {
	
	@Autowired
	private CustomMockMvc customMockMvc;
	@MockBean
	private PessoaUsuariaClient pessoaUsuariaClient;

	@Test
	@DisplayName("Deve criar uma nova conta quando a pessoa usuaria ainda não está numa conta como owner primaria")
	@Transactional
	void test1() throws Exception {
		HashMap<String, Object> json = new HashMap<>();
		json.put("nome", "nome da conta");
		json.put("novaContaOwnerRequest", Map.of("nome","nome","email","email@email.com","senha","123456"));
		
		NovaContaOwnerRequest ownerRequest = new NovaContaOwnerRequest("nome", "email@email.com", "123456");
		NovaPessoaUsuarioRequest pessoaUsuariaRequest = new NovaPessoaUsuarioRequest(ownerRequest);
		UUID idGlobalOwner = UUID.randomUUID();
		Mockito
			.when(pessoaUsuariaClient.criaPessoaUsuaria(pessoaUsuariaRequest))
			.thenReturn(idGlobalOwner);
		
		ResultActions resultado = customMockMvc.post("/api/contas/v1",json);
		
		resultado.andExpect(status().isOk());			
	}
	
	@Test
	@DisplayName("Deve direcionar para um endereco de adicao de contas quando a pessoa usuaria já é owner primaria de uma conta")
	@Transactional
	void test2() throws Exception {
		HashMap<String, Object> json = new HashMap<>();
		json.put("nome", "nome da conta");
		json.put("novaContaOwnerRequest", Map.of("nome","nome","email","email@email.com","senha","123456"));
		
		NovaContaOwnerRequest ownerRequest = new NovaContaOwnerRequest("nome", "email@email.com", "123456");
		NovaPessoaUsuarioRequest pessoaUsuariaRequest = new NovaPessoaUsuarioRequest(ownerRequest);
		UUID idGlobalOwner = UUID.randomUUID();
		Mockito
			.when(pessoaUsuariaClient.criaPessoaUsuaria(pessoaUsuariaRequest))
			.thenReturn(idGlobalOwner);	
		
		//registrando a primeira vez
		customMockMvc.post("/api/contas/v1",json);
		
		ResultActions resultado = customMockMvc.post("/api/contas/v1",json);
		
		resultado.andExpect(status().isFound());
		
		
	}	

}
