package com.avaya.ept.pocs.odataspike.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avaya.ept.pocs.odataspike.domains.OrchestrationContext;

public interface OrchestrationContextRepository extends JpaRepository<OrchestrationContext, UUID> {
}
