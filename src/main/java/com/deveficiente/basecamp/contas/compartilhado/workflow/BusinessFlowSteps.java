package com.deveficiente.basecamp.contas.compartilhado.workflow;

import com.deveficiente.basecamp.contas.compartilhado.Log5WBuilder;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.function.Supplier;

public class BusinessFlowSteps {

	private BusinessFlowEntity currentFlow;
	private TransactionTemplate transactionTemplate;
	private BusinessFlowRepository businessFlowRepository;
	private EntityManager manager;

	private static final Logger log = LoggerFactory
			.getLogger(BusinessFlowSteps.class);

	public BusinessFlowSteps(BusinessFlowEntity currentFlow,
			TransactionTemplate transactionTemplate,
			BusinessFlowRepository businessFlowRepository,
			EntityManager manager) {
		super();
		this.currentFlow = currentFlow;
		this.transactionTemplate = transactionTemplate;
		this.businessFlowRepository = businessFlowRepository;
		this.manager = manager;
	}

	public <T> String executeOnlyOnce(String stepName, Supplier<T> logic) {

		Optional<BusinessFlowStep> possibleStep = businessFlowRepository
				.findStepByName(stepName, currentFlow.getId());

		return

		possibleStep.map(step -> {
			Log5WBuilder.metodo("executeOnlyOnce")
					.oQueEstaAcontecendo("Returning previous execution")
					.adicionaInformacao("stepName", stepName).info(log);

			return step.getExecutionResult();
		}).orElseGet(() -> {

			return transactionTemplate.execute(status -> {
				T executionResult = logic.get();
				/*
				 * Tava dando pau aqui, ou seja precisa mesmo de um controle de
				 * estado mínimo para saber que o meu código de flow falhou.
				 * 
				 */
				BusinessFlowEntity renewedFlow = manager.merge(currentFlow);
				return renewedFlow.registerStep(stepName, executionResult)
						.getExecutionResult();

			});
		});

	}

}
