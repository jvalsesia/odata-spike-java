package com.avaya.ept.pocs.odataspike.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class SurveyContext {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String tentantId;

    @Column(nullable = false)
    private String lastAgentId;

    @Column(nullable = false)
    private String callId;

    @Column(nullable = false)
    private String dnis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_info_id", nullable = false)
    @JsonIgnore
    private CustomerInfo customerInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    @JsonIgnore
    private Agent agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orchestration_context_id", nullable = false)
    @JsonIgnore
    private OrchestrationContext orchestrationContext;

    @OneToMany(mappedBy = "surveyContext")
    @JsonIgnore
    private Set<Survey> surveyContextSurveys;

}
