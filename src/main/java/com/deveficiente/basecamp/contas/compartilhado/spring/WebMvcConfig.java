package com.deveficiente.basecamp.contas.compartilhado.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private ApplicationContext context;

	public WebMvcConfig(ApplicationContext context) {
		super();
		this.context = context;
	}

	@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new MeuFakeAuthenticatedPrincipal());
	}
}