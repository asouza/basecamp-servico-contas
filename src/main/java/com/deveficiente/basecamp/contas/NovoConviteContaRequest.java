package com.deveficiente.basecamp.contas;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class NovoConviteContaRequest {

    @Size(min = 1)
    private List<@NotBlank @Email String> emailsConvidados;
    //coloquei as annotations aqui e, por algum motivo, não estão sendo consideradas na desserializacao.
    //com certeza estou fazendo algo errado, só não sei o que ainda.
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @FutureOrPresent
    @NotNull
    private LocalDate dataExpiracao;

    public NovoConviteContaRequest(List<String> emailsConvidados, LocalDate dataExpiracao) {
        this.emailsConvidados = emailsConvidados;
        this.dataExpiracao = dataExpiracao;
    }


    public List<ConviteConta> toConvites(Conta conta) {
        return emailsConvidados.stream()
                .map(email -> new ConviteConta(email, dataExpiracao, conta))
                .toList();
    }
}
