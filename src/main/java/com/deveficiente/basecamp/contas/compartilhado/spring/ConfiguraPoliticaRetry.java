package com.deveficiente.basecamp.contas.compartilhado.spring;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.deveficiente.basecamp.contas.compartilhado.Log5WBuilder;

import feign.FeignException;
import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;

@Configuration
public class ConfiguraPoliticaRetry {
	
	private static final Logger log = LoggerFactory
			.getLogger(ConfiguraPoliticaRetry.class);
	
	@Autowired
	private RetryRegistry retryRegistry;	
	
	@Bean("retryDefault")
	public Retry configuraRetryDefault() {
		RetryConfig config = RetryConfig.custom()
          	  .maxAttempts(2)
          	  .intervalFunction(IntervalFunction
          			  .ofExponentialBackoff(Duration.ofSeconds(1), 2))
          	  .retryOnException(exception -> {
          		  if(exception instanceof FeignException) {
          			  
          			  FeignException feignException = (FeignException) exception;          			  
          			  
          			  if(feignException.status() == 500) {
          				  Log5WBuilder
          				  .metodo()
          				  .oQueEstaAcontecendo("Tentativa de retry para chamada http ")		            		  	
          				  .adicionaInformacao("status", feignException.status()+"")
          				  //podia ser debug aqui
          				  .info(log);	            			
          				  
          				  return true;	            				  
          			  }
          			  
      				  Log5WBuilder
      				  .metodo()
      				  .oQueEstaAcontecendo("Não vai rolar tentativa de retry para chamada http")		            		  	
      				  .adicionaInformacao("status", feignException.status()+"")
      				  .adicionaInformacao("url", feignException.request().url()+"")
      				  //podia ser debug aqui
      				  .info(log);	            			  
          			  
          		  }
          		  
  				  Log5WBuilder
  				  .metodo()
  				  .oQueEstaAcontecendo("Não vai rolar tentativa de retry para chamada http")		            		  	
  				  .adicionaInformacao("problemaDesconhecido", exception.getClass().toString())
  				  //podia ser debug aqui
  				  .info(log);	            		  
          		  
          		  return false;
          	  })
          	  .failAfterMaxAttempts(true)          	  
          	  .build();
		
		return retryRegistry.retry("retryDefault", config);

	}
	
}
