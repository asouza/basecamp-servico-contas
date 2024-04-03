package com.deveficiente.basecamp.contas.compartilhado.spring;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

/**
 * Classe criada para postergar a configuração do spring security
 * @author albertoluizsouza
 *
 */
public class MeuFakeAuthenticatedPrincipal implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		// Implemente o código para converter o valor do parâmetro de entrada em
		// um objeto MyCustomType
		// Aqui, usamos o HttpServletRequest para obter o valor do parâmetro
		// chamado "myCustomParameter"

		HttpServletRequest request = (HttpServletRequest) webRequest
				.getNativeRequest();
		String authorizationHeader = request.getHeader("Authorization");

		if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Precisa do Token e precisa começar com Bearer");
		}

		String token = authorizationHeader.substring(7);
		
		Assert.hasText(token,"O token não de autorizacao nao pode ser vazio");

		UUID idUsuarioGlobal = UUID.fromString(token);
		
		return idUsuarioGlobal;
	}
}
