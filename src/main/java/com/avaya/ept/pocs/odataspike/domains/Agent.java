package com.avaya.ept.pocs.odataspike.domains;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Agent {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String organizationId;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String displayName;

    @Column(nullable = false)
    private String emailId;

    @Column(nullable = false)
    private String profileId;

    @OneToMany(mappedBy = "agent")
    @JsonIgnore
    private Set<SurveyContext> agentSurveyContexts;

}
