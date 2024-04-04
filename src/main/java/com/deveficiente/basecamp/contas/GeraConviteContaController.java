package com.deveficiente.basecamp.contas;

import com.deveficiente.basecamp.contas.compartilhado.OptionalToHttpStatusException;
import com.deveficiente.basecamp.contas.compartilhado.workflow.BusinessFlowRegister;
import com.deveficiente.basecamp.contas.compartilhado.workflow.BusinessFlowSteps;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class GeraConviteContaController {
    private ContaRepository contaRepository;
    private ConviteRepository conviteRepository;
    private EnviaEmailConvites enviaEmailConvites;
    private BusinessFlowRegister businessFlowRegister;

    public GeraConviteContaController(ContaRepository contaRepository,
                                      ConviteRepository conviteRepository,EnviaEmailConvites enviaEmailConvites,BusinessFlowRegister businessFlowRegister) {
        this.contaRepository = contaRepository;
        this.conviteRepository = conviteRepository;
        this.enviaEmailConvites = enviaEmailConvites;
        this.businessFlowRegister = businessFlowRegister;
    }

    @PostMapping("/api/contas/v1/{idConta}/convites")
    public void geraConvite(@RequestHeader("idempotency-key") String idempotencyKey, @AuthenticationPrincipal UUID idOwnerPrimaria
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

        BusinessFlowSteps workflow = businessFlowRegister
                .execute("geracao-convite-conta", idempotencyKey.concat(idOwnerPrimaria.toString()));

        String idsConvites = workflow.executeOnlyOnce("salva-convites", () -> {
            Set<ConviteConta> convites = conta.geraConvites(request);
            conviteRepository.saveAll(convites);
            return convites
                    .stream()
                    .map(convite -> convite.getId().toString())
                    .collect(Collectors.joining(","));
        });

        workflow.executeOnlyOnce("envia-email-convites",() -> {
            //este codigo é consequencia do uso do workflow
            List<ConviteConta> convites = conviteRepository
                    .findAllById(Stream.of(idsConvites.split(","))
                            .map(Long::valueOf)
                            .toList());

            enviaEmailConvites.executa(convites);

            //nao tinha string para retornar aqui.
            return "emails-enviados";
        });




    }
}
