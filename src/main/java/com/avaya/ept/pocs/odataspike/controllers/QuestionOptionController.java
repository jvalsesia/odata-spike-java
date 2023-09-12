package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.QuestionOption;
import com.avaya.ept.pocs.odataspike.services.QuestionOptionService;

@RestController
@RequestMapping("/questionoption")
public class QuestionOptionController {
    @Autowired
    QuestionOptionService questionOptionService;

    @GetMapping(value = "/list")
    List<QuestionOption> list() {
        return questionOptionService.getQuestionOptionList();
    }
}
