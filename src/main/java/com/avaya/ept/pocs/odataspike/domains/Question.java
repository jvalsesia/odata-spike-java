package com.avaya.ept.pocs.odataspike.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Question {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String externalId;

    @Column(nullable = false)
    private Integer questionOrder;

    @Column(nullable = false)
    private String questionName;

    @ManyToMany(mappedBy = "surveyQuestionQuestions")
    @JsonIgnore
    private Set<Survey> surveyQuestionSurveys;

    @OneToMany(mappedBy = "question")
    @JsonIgnore
    private Set<QuestionOption> questionQuestionOptions;

}
