package com.avaya.ept.pocs.odataspike.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;

//import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class OrchestrationContext {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String contextQueue;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String channelProviderId;

    @Column(nullable = false)
    private String channelProviderAddress;

    @OneToMany(mappedBy = "orchestrationContext", fetch = FetchType.EAGER)
    // @JsonIgnore
    private Set<AttributeList> orchestrationContextAttributeLists;

    @OneToMany(mappedBy = "orchestrationContext", fetch = FetchType.EAGER)
    // @JsonIgnore
    private Set<SurveyContext> orchestrationContextSurveyContexts;

}
