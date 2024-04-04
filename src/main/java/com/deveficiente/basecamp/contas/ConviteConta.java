package com.deveficiente.basecamp.contas;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ConviteConta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private final String email;
    @NotNull
    @FutureOrPresent
    private final LocalDate dataExpiracao;
    @ManyToOne
    @NotNull
    private final Conta conta;
    private UUID idGlobal = UUID.randomUUID();

    public ConviteConta(String email, LocalDate dataExpiracao, Conta conta) {
        this.email = email;
        this.dataExpiracao = dataExpiracao;
        this.conta = conta;
    }

    //#gravarEpisodio equals é metodo que tem a ver com negócio.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConviteConta that = (ConviteConta) o;
        return Objects.equals(email, that.email) && Objects.equals(conta, that.conta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, conta);
    }

    public String getEmail() {
        return this.email;
    }

    public Long getId() {
        return this.id;
    }
}
