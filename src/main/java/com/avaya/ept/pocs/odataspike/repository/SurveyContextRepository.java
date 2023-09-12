package com.avaya.ept.pocs.odataspike.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avaya.ept.pocs.odataspike.domains.SurveyContext;

public interface SurveyContextRepository extends JpaRepository<SurveyContext, UUID> {
    List<SurveyContext> findByOrchestrationContextId(UUID orchestrationContextIUuid);

    List<SurveyContext> findByAgentId(UUID agentUuid);
}
