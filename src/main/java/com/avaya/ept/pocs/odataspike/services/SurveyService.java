package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.Survey;
import com.avaya.ept.pocs.odataspike.repository.SurveyRepository;

@Service
public class SurveyService {
    @Autowired
    SurveyRepository surveyRepository;

    public List<Survey> getSurveytList() {
        List<Survey> list = surveyRepository.findAll();
        return list;
    }
}
