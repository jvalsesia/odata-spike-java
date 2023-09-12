package com.avaya.ept.pocs.odataspike.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class CustomerInfo {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String accountId;

    @Column(nullable = false)
    private String emailAddress;

    @Column(nullable = false)
    private String ani;

    @JsonIgnore
    @OneToMany(mappedBy = "customerInfo")
    private Set<SurveyContext> customerInfoSurveyContexts;

}
