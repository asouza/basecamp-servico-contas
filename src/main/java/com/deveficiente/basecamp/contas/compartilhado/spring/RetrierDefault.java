package com.deveficiente.basecamp.contas.compartilhado.spring;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.github.resilience4j.decorators.Decorators;
import io.github.resilience4j.retry.Retry;

/**
 * Faz o retry utilizando a política padrão estabelecida em 
 * {@link ConfiguraPoliticaRetry}.
 * 
 * @author albertoluizsouza
 *
 */
@Component
public class RetrierDefault {

	private Retry retry;

	public RetrierDefault(@Qualifier("retryDefault") Retry retry) {
		super();
		this.retry = retry;
	}
	
	public <T> T executa(Supplier<T> supplier) {
		return Decorators
				.ofSupplier(supplier)
				.withRetry(retry)
				.get();
	}
	
	
}
