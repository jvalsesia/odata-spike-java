package com.avaya.ept.pocs.odataspike.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

import com.avaya.ept.pocs.odataspike.domains.AttributeList;

import java.util.List;

public interface AttributeListRepository extends JpaRepository<AttributeList, UUID> {

    List<AttributeList> findByOrchestrationContextId(UUID orchestrationContextIUuid);
}
