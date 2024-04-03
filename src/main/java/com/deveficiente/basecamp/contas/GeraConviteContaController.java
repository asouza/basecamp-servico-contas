package com.deveficiente.basecamp.contas;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GeraConviteContaController {
    @PostMapping("/api/contas/v1/{idConta}/convites")
    public void geraConvite(@AuthenticationPrincipal UUID idOwnerPrimaria
            , @PathVariable("idConta") UUID idConta, @Valid @RequestBody NovoConviteContaRequest request) {
        System.out.println("Gerando convite para a conta " + idConta + " com o idOwnerPrimaria " + idOwnerPrimaria);
    }
}
