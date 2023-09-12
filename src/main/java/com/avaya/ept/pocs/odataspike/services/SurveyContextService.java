package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.SurveyContext;
import com.avaya.ept.pocs.odataspike.repository.SurveyContextRepository;

@Service
public class SurveyContextService {
    @Autowired
    SurveyContextRepository surveyContextRepository;

    public List<SurveyContext> getSurveyContextList() {
        List<SurveyContext> list = surveyContextRepository.findAll();
        return list;
    }
}
