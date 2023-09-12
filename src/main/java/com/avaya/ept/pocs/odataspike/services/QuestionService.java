package com.avaya.ept.pocs.odataspike.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.Question;
import com.avaya.ept.pocs.odataspike.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public List<Question> getQuestionList() {
        List<Question> list = questionRepository.findAll();
        return list;
    }
}
