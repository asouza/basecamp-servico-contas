package com.deveficiente.basecamp.contas;

import com.deveficiente.basecamp.contas.compartilhado.OptionalToHttpStatusException;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.UUID;

@RestController
public class GeraConviteContaController {
    private ContaRepository contaRepository;
    private ConviteRepository conviteRepository;
    private EnviaEmailConvites enviaEmailConvites;

    public GeraConviteContaController(ContaRepository contaRepository,
                                      ConviteRepository conviteRepository,EnviaEmailConvites enviaEmailConvites) {
        this.contaRepository = contaRepository;
        this.conviteRepository = conviteRepository;
        this.enviaEmailConvites = enviaEmailConvites;
    }

    @PostMapping("/api/contas/v1/{idConta}/convites")
    public void geraConvite(@AuthenticationPrincipal UUID idOwnerPrimaria
            , @PathVariable("idConta") UUID idConta, @Valid @RequestBody NovoConviteContaRequest request) {

        Conta conta = OptionalToHttpStatusException
                .execute(contaRepository.findByIdGlobal(idConta),
                        404,
                        "Conta inexistente");

        //#paraGravar Regra de negócio já junto do framework, facilitando a transformação do problema para retorno
        //se isso aqui tivesse dentro de um caso de uso, qual seria o retorno para este tipo de problema?
        if(!conta.pertenceAoUsuario(idOwnerPrimaria)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }


        /*
        * #copilotGerou Essa linha foi a gerada pelo copilot
        * Agora pq ele gerou isso? O que ele entendeu do problema que fez ele gerar isso?
        * Meu chute é que o padrão por aí.. neste caso ele ta utilizando a mesma variavel duas vezes na mesma sequencia, o que, para mim é um alerta.
        *
        * E pq ele passou o idOwnerPrimaria?
         */
        //conta.geraConvite(idOwnerPrimaria, request.getDataExpiracao(), request.getEmailsConvidados());

        Set<ConviteConta> convites = conta.geraConvites(request);
        conviteRepository.saveAll(convites);

        enviaEmailConvites.executa(convites);



    }
}
