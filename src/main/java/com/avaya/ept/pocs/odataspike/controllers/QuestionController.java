package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.Question;
import com.avaya.ept.pocs.odataspike.services.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping(value = "/list")
    List<Question> list() {
        return questionService.getQuestionList();
    }
}
