package com.avaya.ept.pocs.odataspike.odata;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.ex.ODataRuntimeException;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.uri.UriParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avaya.ept.pocs.odataspike.domains.Agent;
import com.avaya.ept.pocs.odataspike.domains.Answer;
import com.avaya.ept.pocs.odataspike.domains.AttributeList;
import com.avaya.ept.pocs.odataspike.domains.OrchestrationContext;
import com.avaya.ept.pocs.odataspike.domains.Question;
import com.avaya.ept.pocs.odataspike.domains.QuestionOption;
import com.avaya.ept.pocs.odataspike.domains.Survey;
import com.avaya.ept.pocs.odataspike.domains.SurveyContext;
import com.avaya.ept.pocs.odataspike.repository.AgentRepository;
import com.avaya.ept.pocs.odataspike.repository.AnswerRepository;
import com.avaya.ept.pocs.odataspike.repository.AttributeListRepository;
import com.avaya.ept.pocs.odataspike.repository.CustomerInfoRepository;
import com.avaya.ept.pocs.odataspike.repository.OrchestrationContextRepository;
import com.avaya.ept.pocs.odataspike.repository.QuestionOptionRepository;
import com.avaya.ept.pocs.odataspike.repository.QuestionRepository;
import com.avaya.ept.pocs.odataspike.repository.SurveyContextRepository;
import com.avaya.ept.pocs.odataspike.repository.SurveyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {

    @Autowired
    CustomerInfoRepository customerInfoRepository;
    @Autowired
    SurveyRepository surveyRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionOptionRepository questionOptionRepository;
    @Autowired
    SurveyContextRepository surveyContextRepository;
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    OrchestrationContextRepository orchestrationContextRepository;
    @Autowired
    AttributeListRepository attributeListRepository;

    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) throws ODataApplicationException {

        // actually, this is only required if we have more than one Entity Sets
        switch (edmEntitySet.getName()) {
            case SurveyEdmProvider.ES_CUSTOMER_INFO_NAME: {
                return getCustomerInfos();
            }
            case SurveyEdmProvider.ES_SURVEY_NAME: {
                return getSurveys();
            }
            case SurveyEdmProvider.ES_ANSWER_NAME: {
                return getAnswers();
            }
            case SurveyEdmProvider.ES_QUESTION_NAME: {
                return getQuestions();
            }
            case SurveyEdmProvider.ES_QUESTION_OPTION_NAME: {
                return getQuestionOptions();
            }
            case SurveyEdmProvider.ES_SURVEY_CONTEXT_NAME: {
                return getSurveyContexts();
            }
            case SurveyEdmProvider.ES_AGENT_NAME: {
                return getAgents();
            }
            case SurveyEdmProvider.ES_ORCHESTRATION_CONTEXT_NAME: {
                return getOrchestrationContexts();
            }
            case SurveyEdmProvider.ES_ATTRIBUTE_LIST_NAME: {
                return getAttributeLists();
            }
        }

        return null;
    }

    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams)
            throws ODataApplicationException {

        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        // actually, this is only required if we have more than one Entity Type
        switch (edmEntitySet.getName()) {
            case SurveyEdmProvider.ES_CUSTOMER_INFO_NAME: {
                return getCustomerInfo(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_SURVEY_NAME: {
                return getSurvey(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_ANSWER_NAME: {
                return getAnswer(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_QUESTION_NAME: {
                return getQuestion(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_QUESTION_OPTION_NAME: {
                return getQuestionOption(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_SURVEY_CONTEXT_NAME: {
                return getSurveyContext(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_AGENT_NAME: {
                return getAgent(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_ORCHESTRATION_CONTEXT_NAME: {
                return getOrchestrationContext(edmEntityType, keyParams);
            }
            case SurveyEdmProvider.ES_ATTRIBUTE_LIST_NAME: {
                return getAttributeList(edmEntityType, keyParams);
            }
        }

        return null;
    }

    // Navigation
    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType) {

        EntityCollection collection = getRelatedEntityCollection(entity, relatedEntityType);
        if (collection.getEntities().isEmpty()) {

            return null;
        }
        return collection.getEntities().get(0);
    }

    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType, List<UriParameter> keyPredicates)
            throws ODataApplicationException {
        EntityCollection relatedEntities = getRelatedEntityCollection(entity, relatedEntityType);
        return Util.findEntity(relatedEntityType, relatedEntities, keyPredicates);
    }

    public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
        EntityCollection navigationTargetEntityCollection = new EntityCollection();

        FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        String sourceEntityFqn = sourceEntity.getType();
        log.info("sourceEntity: {}", sourceEntity);
        if (sourceEntityFqn.equals(SurveyEdmProvider.ET_ATTRIBUTE_LIST_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_FQN)) {

            UUID attributeListUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity attributeListUuid: {}", attributeListUuid);
            // relation AttributeLists->OrchestrationContext (result all
            // OrchestrationContext)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getOrchestrationContexts().getEntities());
        } else if (sourceEntityFqn.equals(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_ATTRIBUTE_LIST_FQN)) {

            UUID orchestratioUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity orchestratioUuid: {}", orchestratioUuid);
            // relation OrchestrationContext->AttributesLists (result all products)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getAttributeListsByOrchestrationId(orchestratioUuid).getEntities());
        } else if (sourceEntityFqn.equals(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN)) {

            UUID orchestratioUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity orchestratioUuid: {}", orchestratioUuid);
            // relation OrchestrationContext->AttributesLists (result all products)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getSurveyContextsByOrchestrationId(orchestratioUuid).getEntities());
        } else if (sourceEntityFqn.equals(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_FQN)) {

            UUID surveyContextUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity surveyContextUuid: {}", surveyContextUuid);
            // relation OrchestrationContext->AttributesLists (result all products)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getOrchestrationContexts().getEntities());
        } else if (sourceEntityFqn.equals(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_AGENT_FQN)) {

            UUID surveyContextUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity surveyContextUuid: {}", surveyContextUuid);
            // relation OrchestrationContext->AttributesLists (result all products)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getAgents().getEntities());
        } else if (sourceEntityFqn.equals(SurveyEdmProvider.ET_AGENT_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN)) {

            UUID agentUuid = (UUID) sourceEntity.getProperty("Id").getValue();
            log.info("sourceEntity agentUuid: {}", agentUuid);
            // relation OrchestrationContext->AttributesLists (result all products)
            navigationTargetEntityCollection.getEntities()
                    .addAll(getSurveyContextsByAgentId(agentUuid).getEntities());
        }
        if (navigationTargetEntityCollection.getEntities().isEmpty()) {
            return null;
        }
        return navigationTargetEntityCollection;
    }

    private URI createId(String entitySetName, Object id) {
        try {
            return new URI(entitySetName + "(" + String.valueOf(id) + ")");
        } catch (URISyntaxException e) {
            throw new ODataRuntimeException("Unable to create id for entity: " + entitySetName, e);
        }
    }

    private Entity getCustomerInfo(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getCustomerInfos();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getCustomerInfos() {
        EntityCollection retEntitySet = new EntityCollection();

        List<Survey> surveys = surveyRepository.findAll();
        for (Survey survey : surveys) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, survey.getId()))
                    .addProperty(
                            new Property(null, "EngagementId", ValueType.PRIMITIVE, survey.getEngagementId()))
                    .addProperty(
                            new Property(null, "Name", ValueType.PRIMITIVE, survey.getName()))
                    .addProperty(new Property(null, "Language", ValueType.PRIMITIVE, survey.getLanguage()))
                    .addProperty(
                            new Property(null, "ChannelId", ValueType.PRIMITIVE, survey.getChannelId()))

                    .addProperty(
                            new Property(null, "Reason", ValueType.PRIMITIVE, survey.getReason()))
                    .addProperty(
                            new Property(null, "StartDate", ValueType.PRIMITIVE, survey.getStartDate()))
                    .addProperty(
                            new Property(null, "EndDate", ValueType.PRIMITIVE, survey.getEndDate()));
            entity.setType(SurveyEdmProvider.ET_CUSTOMER_INFO_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_CUSTOMER_INFO_NAME, survey.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getSurvey(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getSurveys();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getSurveys() {
        EntityCollection retEntitySet = new EntityCollection();

        List<Survey> surveys = surveyRepository.findAll();
        for (Survey survey : surveys) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, survey.getId()))
                    .addProperty(
                            new Property(null, "EngagementId", ValueType.PRIMITIVE, survey.getEngagementId()))
                    .addProperty(
                            new Property(null, "Name", ValueType.PRIMITIVE, survey.getName()))
                    .addProperty(new Property(null, "Language", ValueType.PRIMITIVE, survey.getLanguage()))
                    .addProperty(
                            new Property(null, "ChannelId", ValueType.PRIMITIVE, survey.getChannelId()))

                    .addProperty(
                            new Property(null, "Reason", ValueType.PRIMITIVE, survey.getReason()))
                    .addProperty(
                            new Property(null, "StartDate", ValueType.PRIMITIVE, survey.getStartDate()))
                    .addProperty(
                            new Property(null, "EndDate", ValueType.PRIMITIVE, survey.getEndDate()));
            entity.setType(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_SURVEY_NAME, survey.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getAnswer(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getAnswers();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getAnswers() {
        EntityCollection retEntitySet = new EntityCollection();

        List<Answer> answers = answerRepository.findAll();
        for (Answer answer : answers) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, answer.getId()))
                    .addProperty(new Property(null, "ExternalId", ValueType.PRIMITIVE, answer.getExternalId()))
                    .addProperty(new Property(null, "AnswerType", ValueType.PRIMITIVE, answer.getAnswerType()))
                    .addProperty(new Property(null, "Result", ValueType.PRIMITIVE, answer.getResult()));
            entity.setType(SurveyEdmProvider.ET_ANSWER_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_ANSWER_NAME, answer.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getQuestion(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getQuestions();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getQuestions() {
        EntityCollection retEntitySet = new EntityCollection();

        List<Question> questions = questionRepository.findAll();
        for (Question question : questions) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, question.getId()))
                    .addProperty(
                            new Property(null, "ExternalId", ValueType.PRIMITIVE, question.getExternalId()))
                    .addProperty(
                            new Property(null, "QuestionOrder", ValueType.PRIMITIVE,
                                    question.getQuestionOrder()))
                    .addProperty(new Property(null, "QuestionName", ValueType.PRIMITIVE,
                            question.getQuestionName()));
            entity.setType(SurveyEdmProvider.ET_QUESTION_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_QUESTION_NAME, question.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getQuestionOption(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getQuestionOptions();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getQuestionOptions() {
        EntityCollection retEntitySet = new EntityCollection();

        List<QuestionOption> questionOptions = questionOptionRepository.findAll();
        for (QuestionOption questionOption : questionOptions) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, questionOption.getId()))
                    .addProperty(
                            new Property(null, "ExternalId", ValueType.PRIMITIVE,
                                    questionOption.getExternalId()))
                    .addProperty(
                            new Property(null, "OptionValue", ValueType.PRIMITIVE,
                                    questionOption.getOptionValue()))
                    .addProperty(new Property(null, "Result", ValueType.PRIMITIVE,
                            questionOption.getResult()));
            entity.setType(SurveyEdmProvider.ET_QUESTION_OPTION_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_QUESTION_OPTION_NAME, questionOption.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getSurveyContext(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getSurveyContexts();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getSurveyContexts() {
        EntityCollection retEntitySet = new EntityCollection();

        List<SurveyContext> surveyContexts = surveyContextRepository.findAll();
        for (SurveyContext surveyContext : surveyContexts) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, surveyContext.getId()))
                    .addProperty(
                            new Property(null, "TentantId", ValueType.PRIMITIVE,
                                    surveyContext.getTentantId()))
                    .addProperty(
                            new Property(null, "LastAgentId", ValueType.PRIMITIVE,
                                    surveyContext.getLastAgentId()))
                    .addProperty(new Property(null, "CallId", ValueType.PRIMITIVE,
                            surveyContext.getCallId()))
                    .addProperty(new Property(null, "Dnis", ValueType.PRIMITIVE,
                            surveyContext.getDnis()));
            entity.setType(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_SURVEY_CONTEXT_NAME, surveyContext.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private EntityCollection getSurveyContextsByOrchestrationId(UUID orchestrationContextUuid) {
        EntityCollection retEntitySet = new EntityCollection();
        List<SurveyContext> surveyContexts = surveyContextRepository
                .findByOrchestrationContextId(orchestrationContextUuid);
        for (SurveyContext surveyContext : surveyContexts) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, surveyContext.getId()))
                    .addProperty(
                            new Property(null, "TentantId", ValueType.PRIMITIVE,
                                    surveyContext.getTentantId()))
                    .addProperty(
                            new Property(null, "LastAgentId", ValueType.PRIMITIVE,
                                    surveyContext.getLastAgentId()))
                    .addProperty(new Property(null, "CallId", ValueType.PRIMITIVE,
                            surveyContext.getCallId()))
                    .addProperty(new Property(null, "Dnis", ValueType.PRIMITIVE,
                            surveyContext.getDnis()));
            entity.setType(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_SURVEY_CONTEXT_NAME, surveyContext.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private EntityCollection getSurveyContextsByAgentId(UUID agentUuid) {
        EntityCollection retEntitySet = new EntityCollection();
        List<SurveyContext> surveyContexts = surveyContextRepository
                .findByAgentId(agentUuid);
        for (SurveyContext surveyContext : surveyContexts) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, surveyContext.getId()))
                    .addProperty(
                            new Property(null, "TentantId", ValueType.PRIMITIVE,
                                    surveyContext.getTentantId()))
                    .addProperty(
                            new Property(null, "LastAgentId", ValueType.PRIMITIVE,
                                    surveyContext.getLastAgentId()))
                    .addProperty(new Property(null, "CallId", ValueType.PRIMITIVE,
                            surveyContext.getCallId()))
                    .addProperty(new Property(null, "Dnis", ValueType.PRIMITIVE,
                            surveyContext.getDnis()));
            entity.setType(SurveyEdmProvider.ET_SURVEY_CONTEXT_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_SURVEY_CONTEXT_NAME, surveyContext.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private Entity getAgent(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getAgents();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getAgents() {
        EntityCollection retEntitySet = new EntityCollection();

        List<Agent> agents = agentRepository.findAll();
        for (Agent agent : agents) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, agent.getId()))
                    .addProperty(
                            new Property(null, "UserId", ValueType.PRIMITIVE,
                                    agent.getUserId()))
                    .addProperty(
                            new Property(null, "OrganizationId", ValueType.PRIMITIVE,
                                    agent.getOrganizationId()))
                    .addProperty(
                            new Property(null, "LoginId", ValueType.PRIMITIVE,
                                    agent.getLoginId()))
                    .addProperty(
                            new Property(null, "LastName", ValueType.PRIMITIVE,
                                    agent.getLastName()))
                    .addProperty(
                            new Property(null, "FirstName", ValueType.PRIMITIVE,
                                    agent.getFirstName()))
                    .addProperty(
                            new Property(null, "DisplayName", ValueType.PRIMITIVE,
                                    agent.getDisplayName()))
                    .addProperty(
                            new Property(null, "EmailId", ValueType.PRIMITIVE,
                                    agent.getEmailId()))
                    .addProperty(new Property(null, "ProfileId", ValueType.PRIMITIVE,
                            agent.getProfileId()));
            entity.setType(SurveyEdmProvider.ET_AGENT_FQN.getFullQualifiedNameAsString());
            entity.setId(createId(SurveyEdmProvider.ET_AGENT_NAME, agent.getId()));
            retEntitySet.getEntities().add(entity);
        }
        return retEntitySet;
    }

    private Entity getOrchestrationContext(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getOrchestrationContexts();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getOrchestrationContexts() {
        EntityCollection retEntitySet = new EntityCollection();

        List<OrchestrationContext> orchestrationContexts = orchestrationContextRepository.findAll();
        for (OrchestrationContext orchestrationContext : orchestrationContexts) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, orchestrationContext.getId()))
                    .addProperty(
                            new Property(null, "ContextQueue", ValueType.PRIMITIVE,
                                    orchestrationContext.getContextQueue()))
                    .addProperty(
                            new Property(null, "UserId", ValueType.PRIMITIVE,
                                    orchestrationContext.getUserId()))
                    .addProperty(new Property(null, "ChannelProviderId", ValueType.PRIMITIVE,
                            orchestrationContext.getChannelProviderId()))
                    .addProperty(new Property(null, "ChannelProviderAddress", ValueType.PRIMITIVE,
                            orchestrationContext.getChannelProviderAddress()));
            entity.setType(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_FQN.getFullQualifiedNameAsString());
            entity.setId(
                    createId(SurveyEdmProvider.ET_ORCHESTRATION_CONTEXT_NAME, orchestrationContext.getId()));
            retEntitySet.getEntities().add(entity);
        }
        return retEntitySet;
    }

    private Entity getAttributeList(EdmEntityType edmEntityType, List<UriParameter> keyParams) {
        // the list of entities at runtime
        EntityCollection entityCollection = getAttributeLists();

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entityCollection, keyParams);

    }

    private EntityCollection getAttributeLists() {
        EntityCollection retEntitySet = new EntityCollection();
        List<AttributeList> attributeLists = attributeListRepository.findAll();
        for (AttributeList attributeList : attributeLists) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, attributeList.getId()))
                    .addProperty(
                            new Property(null, "AttributeValue", ValueType.PRIMITIVE,
                                    attributeList.getAttributeValue()));
            entity.setType(SurveyEdmProvider.ET_ATTRIBUTE_LIST_FQN.getFullQualifiedNameAsString());
            entity.setId(
                    createId(SurveyEdmProvider.ET_ATTRIBUTE_LIST_NAME, attributeList.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

    private EntityCollection getAttributeListsByOrchestrationId(UUID orchestrationContextUuid) {
        EntityCollection retEntitySet = new EntityCollection();
        List<AttributeList> attributeLists = attributeListRepository
                .findByOrchestrationContextId(orchestrationContextUuid);
        for (AttributeList attributeList : attributeLists) {
            Entity entity = new Entity()
                    .addProperty(new Property(null, "Id", ValueType.PRIMITIVE, attributeList.getId()))
                    .addProperty(
                            new Property(null, "AttributeValue", ValueType.PRIMITIVE,
                                    attributeList.getAttributeValue()));
            entity.setType(SurveyEdmProvider.ET_ATTRIBUTE_LIST_FQN.getFullQualifiedNameAsString());
            entity.setId(
                    createId(SurveyEdmProvider.ET_ATTRIBUTE_LIST_NAME, attributeList.getId()));
            retEntitySet.getEntities().add(entity);
        }

        return retEntitySet;
    }

}
