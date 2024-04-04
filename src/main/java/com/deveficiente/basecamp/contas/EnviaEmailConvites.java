package com.deveficiente.basecamp.contas;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EnviaEmailConvites {

    /*
     * Criei essa classe pq a realizaçõa deste envio exige mandar um from, titulo etc...
     */
    public void executa(Set<ConviteConta> convites) {
        //TODO implementar o servico externo para envio de emails
        convites.stream().forEach(convite -> {
            System.out.println("Enviando email para " + convite.getEmail());
        });
    }



}
