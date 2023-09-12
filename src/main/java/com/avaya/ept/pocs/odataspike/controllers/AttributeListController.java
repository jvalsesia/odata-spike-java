package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.AttributeList;
import com.avaya.ept.pocs.odataspike.services.AttributeListService;

@RestController
@RequestMapping("/attributelist")
public class AttributeListController {

    @Autowired
    AttributeListService attributeListService;

    @GetMapping(value = "/list")
    List<AttributeList> list() {
        return attributeListService.getAttributeListList();
    }
}
