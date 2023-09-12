package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.OrchestrationContext;
import com.avaya.ept.pocs.odataspike.services.OrchestrationContextService;

@RestController
@RequestMapping("/orchestrationcontext")
public class OrchestrationContextController {
    @Autowired
    OrchestrationContextService orchestrationContextService;

    @GetMapping(value = "/list")
    List<OrchestrationContext> list() {
        return orchestrationContextService.getOrchestrationContextList();
    }
}
