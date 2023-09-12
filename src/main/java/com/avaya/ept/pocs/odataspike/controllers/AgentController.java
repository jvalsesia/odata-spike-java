package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.Agent;
import com.avaya.ept.pocs.odataspike.services.AgentService;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Autowired
    AgentService agentService;

    @GetMapping(value = "/list")
    List<Agent> list() {
        return agentService.getAgentList();
    }
}
