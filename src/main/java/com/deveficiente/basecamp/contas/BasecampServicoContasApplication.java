package com.deveficiente.basecamp.contas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
public class BasecampServicoContasApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasecampServicoContasApplication.class, args);
	}

}
