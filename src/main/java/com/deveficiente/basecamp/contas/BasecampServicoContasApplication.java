package com.deveficiente.basecamp.contas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BasecampServicoContasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasecampServicoContasApplication.class, args);
	}

}
