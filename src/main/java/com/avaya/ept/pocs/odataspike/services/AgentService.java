package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.Agent;
import com.avaya.ept.pocs.odataspike.repository.AgentRepository;

@Service
public class AgentService {
    @Autowired
    AgentRepository agentRepository;

    public List<Agent> getAgentList() {
        return agentRepository.findAll();
    }
}
