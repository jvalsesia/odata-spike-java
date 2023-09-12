package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.QuestionOption;
import com.avaya.ept.pocs.odataspike.repository.QuestionOptionRepository;

@Service
public class QuestionOptionService {
    @Autowired
    QuestionOptionRepository questionOptionRepository;

    public List<QuestionOption> getQuestionOptionList() {
        List<QuestionOption> list = questionOptionRepository.findAll();
        return list;
    }
}
