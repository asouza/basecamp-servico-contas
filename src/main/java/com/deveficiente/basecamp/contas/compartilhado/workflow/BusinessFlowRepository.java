package com.deveficiente.basecamp.contas.compartilhado.workflow;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BusinessFlowRepository extends JpaRepository<BusinessFlowEntity, Long>{

	BusinessFlowEntity getByUniqueFlowCode(@NotBlank String uniqueFlowCode);

	@Query("select bfs from BusinessFlowStep bfs where stepName = :stepName and bfs.businessFlowEntity.id = :businessFlowId")
	Optional<BusinessFlowStep> findStepByName(@Param("stepName") String stepName, @Param("businessFlowId") Long businessFlowId);

}
