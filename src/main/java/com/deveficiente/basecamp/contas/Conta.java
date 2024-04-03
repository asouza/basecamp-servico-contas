package com.deveficiente.basecamp.contas;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

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

	public boolean pertenceAoUsuario(UUID idOwnerPrimaria) {
		return this.idOwnerPrimaria.equals(idOwnerPrimaria);
	}

    public Set<ConviteConta> geraConvites(NovoConviteContaRequest request) {

		/*
		* #copilotGerou
		* Olha de novo o código gerado pelo copilot...  Basicamente um script....
		 */
//		for (String email : request.getEmailsConvidados()) {
//			ConviteConta convite = new ConviteConta(email, request.getDataExpiracao());
//			convitesGerados.add(convite);
//		}

		//se eu quiser é só extrair uma interface expondo o método toConvites
		List<ConviteConta> convitesGerados = request.toConvites(this);
		HashSet<ConviteConta> convitesUnicos = new HashSet<>(convitesGerados);

		//exemplo de pós condicao
		Assert.isTrue(convitesUnicos.size() == convitesGerados.size(),"O número de convites unicos está diferente do numero de convites solicitados originalmente. Deve ter email duplicado na lista original");

		return convitesUnicos;
    }
}
