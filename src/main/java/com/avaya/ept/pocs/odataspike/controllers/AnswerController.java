package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.Answer;
import com.avaya.ept.pocs.odataspike.services.AnswerService;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @GetMapping(value = "/list")
    List<Answer> list() {
        return answerService.getAnswerList();
    }
}
