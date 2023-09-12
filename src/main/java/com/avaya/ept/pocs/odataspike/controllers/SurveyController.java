package com.avaya.ept.pocs.odataspike.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.domains.Survey;
import com.avaya.ept.pocs.odataspike.services.SurveyService;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    @Autowired
    SurveyService surveyService;

    @GetMapping(value = "/list")
    List<Survey> list() {
        return surveyService.getSurveytList();
    }
}
