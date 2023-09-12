package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.OrchestrationContext;
import com.avaya.ept.pocs.odataspike.repository.OrchestrationContextRepository;

@Service
public class OrchestrationContextService {
    @Autowired
    OrchestrationContextRepository orchestrationContextRepository;

    public List<OrchestrationContext> getOrchestrationContextList() {
        return orchestrationContextRepository.findAll();
    }
}
