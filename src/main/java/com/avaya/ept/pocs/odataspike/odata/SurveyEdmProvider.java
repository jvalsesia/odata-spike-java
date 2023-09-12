package com.avaya.ept.pocs.odataspike.odata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;
import org.springframework.stereotype.Component;

@Component
public class SurveyEdmProvider extends CsdlAbstractEdmProvider {

        // Service Namespace
        public static final String NAMESPACE = "OData.HCS.Survey";

        // EDM Container
        public static final String CONTAINER_NAME = "Container";
        public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

        // Entity Types Names
        public static final String ET_CUSTOMER_INFO_NAME = "CustomerInfo";
        public static final String ET_SURVEY_NAME = "Survey";
        public static final String ET_ANSWER_NAME = "Answer";
        public static final String ET_QUESTION_NAME = "Question";
        public static final String ET_QUESTION_OPTION_NAME = "QuestionOption";
        public static final String ET_SURVEY_CONTEXT_NAME = "SurveyContext";
        public static final String ET_AGENT_NAME = "Agent";
        public static final String ET_ORCHESTRATION_CONTEXT_NAME = "OrchestrationContext";
        public static final String ET_ATTRIBUTE_LIST_NAME = "AttributeList";

        public static final FullQualifiedName ET_CUSTOMER_INFO_FQN = new FullQualifiedName(NAMESPACE,
                        ET_CUSTOMER_INFO_NAME);
        public static final FullQualifiedName ET_SURVEY_FQN = new FullQualifiedName(NAMESPACE,
                        ET_SURVEY_NAME);
        public static final FullQualifiedName ET_ANSWER_FQN = new FullQualifiedName(NAMESPACE,
                        ET_ANSWER_NAME);
        public static final FullQualifiedName ET_QUESTION_FQN = new FullQualifiedName(NAMESPACE,
                        ET_QUESTION_NAME);
        public static final FullQualifiedName ET_QUESTION_OPTION_FQN = new FullQualifiedName(NAMESPACE,
                        ET_QUESTION_OPTION_NAME);
        public static final FullQualifiedName ET_SURVEY_CONTEXT_FQN = new FullQualifiedName(NAMESPACE,
                        ET_SURVEY_CONTEXT_NAME);
        public static final FullQualifiedName ET_AGENT_FQN = new FullQualifiedName(NAMESPACE,
                        ET_AGENT_NAME);
        public static final FullQualifiedName ET_ORCHESTRATION_CONTEXT_FQN = new FullQualifiedName(NAMESPACE,
                        ET_ORCHESTRATION_CONTEXT_NAME);
        public static final FullQualifiedName ET_ATTRIBUTE_LIST_FQN = new FullQualifiedName(NAMESPACE,
                        ET_ATTRIBUTE_LIST_NAME);

        // Entity Set Names
        public static final String ES_CUSTOMER_INFO_NAME = "CustomerInfos";
        public static final String ES_SURVEY_NAME = "Surveys";
        public static final String ES_ANSWER_NAME = "Answers";
        public static final String ES_QUESTION_NAME = "Questions";
        public static final String ES_QUESTION_OPTION_NAME = "QuestionOptions";
        public static final String ES_SURVEY_CONTEXT_NAME = "SurveyContexts";
        public static final String ES_AGENT_NAME = "Agents";
        public static final String ES_ORCHESTRATION_CONTEXT_NAME = "OrchestrationContexts";
        public static final String ES_ATTRIBUTE_LIST_NAME = "AttributeLists";

        // getEntityContainer() Here we provide a Container element that is necessary to
        // host the EntitySet.
        @Override
        public CsdlEntityContainer getEntityContainer() throws ODataException {
                // create EntitySets
                List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
                entitySets.add(getEntitySet(CONTAINER, ES_CUSTOMER_INFO_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_SURVEY_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_ANSWER_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_QUESTION_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_QUESTION_OPTION_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_SURVEY_CONTEXT_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_AGENT_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_ORCHESTRATION_CONTEXT_NAME));
                entitySets.add(getEntitySet(CONTAINER, ES_ATTRIBUTE_LIST_NAME));

                // create EntityContainer
                CsdlEntityContainer entityContainer = new CsdlEntityContainer();
                entityContainer.setName(CONTAINER_NAME);
                entityContainer.setEntitySets(entitySets);

                return entityContainer;
        }

        // getEntityContainerInfo() Information about the EntityContainer to be
        // displayed in the Service Document
        @Override
        public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName)
                        throws ODataException {
                // This method is invoked when displaying the service document at e.g.
                if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
                        CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
                        entityContainerInfo.setContainerName(CONTAINER);
                        return entityContainerInfo;
                }

                return null;

        }

        // getEntitySet() Here we state that the list of CustomerInfos can be called via
        // the
        // EntitySet “CustomerInfos”
        @Override
        public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName)
                        throws ODataException {
                if (entityContainer.equals(CONTAINER)) {
                        CsdlEntitySet entitySet = new CsdlEntitySet();

                        if (entitySetName.equals(ES_CUSTOMER_INFO_NAME)) {
                                entitySet.setName(ES_CUSTOMER_INFO_NAME);
                                entitySet.setType(ET_CUSTOMER_INFO_FQN);
                        } else if (entitySetName.equals(ES_SURVEY_NAME)) {
                                entitySet.setName(ES_SURVEY_NAME);
                                entitySet.setType(ET_SURVEY_FQN);
                        } else if (entitySetName.equals(ES_ANSWER_NAME)) {
                                entitySet.setName(ES_ANSWER_NAME);
                                entitySet.setType(ET_ANSWER_FQN);
                        } else if (entitySetName.equals(ES_QUESTION_NAME)) {
                                entitySet.setName(ES_QUESTION_NAME);
                                entitySet.setType(ET_QUESTION_FQN);
                        } else if (entitySetName.equals(ES_QUESTION_OPTION_NAME)) {
                                entitySet.setName(ES_QUESTION_OPTION_NAME);
                                entitySet.setType(ET_QUESTION_OPTION_FQN);
                        } else if (entitySetName.equals(ES_SURVEY_CONTEXT_NAME)) {
                                entitySet.setName(ES_SURVEY_CONTEXT_NAME);
                                entitySet.setType(ET_SURVEY_CONTEXT_FQN);

                                // navigation many-to-one
                                CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
                                navPropBinding.setTarget(ES_ORCHESTRATION_CONTEXT_NAME); // the target entity set, where
                                                                                         // the navigation
                                                                                         // OrchestrationContexts
                                // property points to
                                navPropBinding.setPath(ET_ORCHESTRATION_CONTEXT_NAME); // the path from entity type to
                                                                                       // navigation property
                                                                                       // OrchestrationContext
                                                                                       // without (s)

                                // navigation many-to-one
                                CsdlNavigationPropertyBinding navPropBindingAgent = new CsdlNavigationPropertyBinding();
                                navPropBindingAgent.setTarget(ES_AGENT_NAME); // the target entity set, where
                                                                              // the navigation
                                                                              // Agents
                                // property points to
                                navPropBindingAgent.setPath(ET_AGENT_NAME); // the path from entity type to
                                                                            // navigation property
                                                                            // Agent
                                                                            // without (s)

                                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
                                navPropBindingList.add(navPropBinding);
                                navPropBindingList.add(navPropBindingAgent);
                                entitySet.setNavigationPropertyBindings(navPropBindingList);
                        } else if (entitySetName.equals(ES_AGENT_NAME)) {
                                entitySet.setName(ES_AGENT_NAME);
                                entitySet.setType(ET_AGENT_FQN);

                                CsdlNavigationPropertyBinding navPropBindingSurveyContext = new CsdlNavigationPropertyBinding();
                                // navigation one-to-many
                                navPropBindingSurveyContext.setTarget(ES_SURVEY_CONTEXT_NAME); // the target entity set,
                                                                                               // where
                                // the navigation SurveyContexts
                                // property points to
                                navPropBindingSurveyContext.setPath(ES_SURVEY_CONTEXT_NAME); // the path from entity
                                                                                             // type to navigation
                                                                                             // property SurveyContexts

                                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();

                                navPropBindingList.add(navPropBindingSurveyContext);
                                entitySet.setNavigationPropertyBindings(navPropBindingList);
                        } else if (entitySetName.equals(ES_ORCHESTRATION_CONTEXT_NAME)) {

                                entitySet.setName(ES_ORCHESTRATION_CONTEXT_NAME);
                                entitySet.setType(ET_ORCHESTRATION_CONTEXT_FQN);
                                // navigation one-to-many
                                CsdlNavigationPropertyBinding navPropBindingAttributeList = new CsdlNavigationPropertyBinding();
                                navPropBindingAttributeList.setTarget(ES_ATTRIBUTE_LIST_NAME); // the target entity set,
                                                                                               // where
                                // the navigation AttributeLists
                                // property points to
                                navPropBindingAttributeList.setPath(ES_ATTRIBUTE_LIST_NAME); // the path from entity
                                                                                             // type to navigation
                                                                                             // property AttributeLists
                                CsdlNavigationPropertyBinding navPropBindingSurveyContext = new CsdlNavigationPropertyBinding();
                                // navigation one-to-many
                                navPropBindingSurveyContext.setTarget(ES_SURVEY_CONTEXT_NAME); // the target entity set,
                                                                                               // where
                                // the navigation SurveyContexts
                                // property points to
                                navPropBindingSurveyContext.setPath(ES_SURVEY_CONTEXT_NAME); // the path from entity
                                                                                             // type to
                                // navigation property SurveyContexts

                                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
                                navPropBindingList.add(navPropBindingAttributeList);
                                navPropBindingList.add(navPropBindingSurveyContext);
                                entitySet.setNavigationPropertyBindings(navPropBindingList);

                        } else if (entitySetName.equals(ES_ATTRIBUTE_LIST_NAME)) {

                                entitySet.setName(ES_ATTRIBUTE_LIST_NAME);
                                entitySet.setType(ET_ATTRIBUTE_LIST_FQN);
                                // navigation many-to-one
                                CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
                                navPropBinding.setTarget(ES_ORCHESTRATION_CONTEXT_NAME); // the target entity set, where
                                                                                         // the navigation
                                                                                         // OrchestrationContexts
                                // property points to
                                navPropBinding.setPath(ET_ORCHESTRATION_CONTEXT_NAME); // the path from entity type to
                                                                                       // navigation property
                                                                                       // OrchestrationContext
                                                                                       // without (s)
                                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
                                navPropBindingList.add(navPropBinding);
                                entitySet.setNavigationPropertyBindings(navPropBindingList);

                        }

                        return entitySet;
                }
                return null;
        }

        // getEntityType() Here we declare the EntityType “CustomerInfo” and a few of
        // its
        // properties
        @Override
        public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
                // this method is called for one of the EntityTypes that are configured in the
                // Schema
                if (entityTypeName.equals(ET_CUSTOMER_INFO_FQN)) {

                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty name = new CsdlProperty().setName("AccountId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty description = new CsdlProperty().setName("EmailAddress")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty ani = new CsdlProperty().setName("Ani")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_CUSTOMER_INFO_NAME);
                        entityType.setProperties(Arrays.asList(id, name, description, ani));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        return entityType;
                } else if (entityTypeName.equals(ET_SURVEY_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty engagementId = new CsdlProperty().setName("EngagementId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty name = new CsdlProperty().setName("Name")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty language = new CsdlProperty().setName("Language")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty channelId = new CsdlProperty().setName("ChannelId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty reason = new CsdlProperty().setName("Reason")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty startDate = new CsdlProperty().setName("StartDate")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty endDate = new CsdlProperty().setName("EndDate")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_SURVEY_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, engagementId, name, language, channelId, reason, startDate,
                                                        endDate));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        return entityType;
                } else if (entityTypeName.equals(ET_ANSWER_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty externalId = new CsdlProperty().setName("ExternalId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty answerType = new CsdlProperty().setName("AnswerType")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty result = new CsdlProperty().setName("Result")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_ANSWER_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, externalId, answerType, result));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        return entityType;
                } else if (entityTypeName.equals(ET_QUESTION_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty externalId = new CsdlProperty().setName("ExternalId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty questionOrder = new CsdlProperty().setName("QuestionOrder")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty questionName = new CsdlProperty().setName("QuestionName")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_QUESTION_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, externalId, questionOrder, questionName));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        return entityType;
                } else if (entityTypeName.equals(ET_QUESTION_OPTION_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty externalId = new CsdlProperty().setName("ExternalId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty optionValue = new CsdlProperty().setName("OptionValue")
                                        .setType(EdmPrimitiveTypeKind.Int64.getFullQualifiedName());
                        CsdlProperty result = new CsdlProperty().setName("Result")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_QUESTION_OPTION_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, externalId, optionValue, result));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        return entityType;
                } else if (entityTypeName.equals(ET_SURVEY_CONTEXT_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty tentantId = new CsdlProperty().setName("TentantId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty lastAgentId = new CsdlProperty().setName("LastAgentId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty callId = new CsdlProperty().setName("CallId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty dnis = new CsdlProperty().setName("Dnis")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // navigation property: many-to-one, null not allowed (SurveyContext must have a
                        // OrchestrationContext)
                        CsdlNavigationProperty navPropOrchestrationContext = new CsdlNavigationProperty()
                                        .setName(ET_ORCHESTRATION_CONTEXT_NAME) // OrchestrationContext
                                        .setType(ET_ORCHESTRATION_CONTEXT_FQN).setNullable(false)
                                        .setPartner(ES_SURVEY_CONTEXT_NAME); // SurveyContexts

                        // navigation property: many-to-one, null not allowed (SurveyContext must have a
                        // OrchestrationContext)
                        CsdlNavigationProperty navPropAgent = new CsdlNavigationProperty()
                                        .setName(ET_AGENT_NAME) // Agent
                                        .setType(ET_AGENT_FQN).setNullable(false)
                                        .setPartner(ES_SURVEY_CONTEXT_NAME); // SurveyContexts

                        List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
                        navPropList.add(navPropOrchestrationContext);
                        navPropList.add(navPropAgent);

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_SURVEY_CONTEXT_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, tentantId, lastAgentId, callId, dnis));
                        entityType.setKey(Collections.singletonList(propertyRef));

                        entityType.setNavigationProperties(navPropList);

                        return entityType;
                } else if (entityTypeName.equals(ET_AGENT_FQN)) {
                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty userId = new CsdlProperty().setName("UserId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty organizationId = new CsdlProperty().setName("OrganizationId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty loginId = new CsdlProperty().setName("LoginId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty lastName = new CsdlProperty().setName("LastName")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty firstName = new CsdlProperty().setName("FirstName")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty displayName = new CsdlProperty().setName("DisplayName")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty emailId = new CsdlProperty().setName("EmailId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty profileId = new CsdlProperty().setName("ProfileId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");

                        // navigation property: one-to-many
                        CsdlNavigationProperty navPropSurveContext = new CsdlNavigationProperty()
                                        .setName(ES_SURVEY_CONTEXT_NAME) // SurveyContexts
                                        .setType(ET_SURVEY_CONTEXT_FQN)
                                        .setCollection(true)
                                        .setPartner(ET_AGENT_NAME); // Agent

                        List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
                        navPropList.add(navPropSurveContext);

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_AGENT_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, userId, organizationId, loginId, lastName, firstName,
                                                        displayName, emailId, profileId));
                        entityType.setKey(Collections.singletonList(propertyRef));
                        entityType.setNavigationProperties(navPropList);
                        return entityType;
                } else if (entityTypeName.equals(ET_ORCHESTRATION_CONTEXT_FQN)) {

                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty contextQueue = new CsdlProperty().setName("ContextQueue")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty userId = new CsdlProperty().setName("UserId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty channelProviderId = new CsdlProperty().setName("ChannelProviderId")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
                        CsdlProperty channelProviderAddress = new CsdlProperty().setName("ChannelProviderAddress")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");
                        // navigation property: one-to-many
                        CsdlNavigationProperty navPropAttributeList = new CsdlNavigationProperty()
                                        .setName(ES_ATTRIBUTE_LIST_NAME) // AttributeLists
                                        .setType(ET_ATTRIBUTE_LIST_FQN)
                                        .setCollection(true)
                                        .setPartner(ET_ORCHESTRATION_CONTEXT_NAME); // OrchestrationContext

                        // navigation property: one-to-many
                        CsdlNavigationProperty navPropSurveContext = new CsdlNavigationProperty()
                                        .setName(ES_SURVEY_CONTEXT_NAME) // SurveyContexts
                                        .setType(ET_SURVEY_CONTEXT_FQN)
                                        .setCollection(true)
                                        .setPartner(ET_ORCHESTRATION_CONTEXT_NAME); // OrchestrationContext

                        List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
                        navPropList.add(navPropAttributeList);
                        navPropList.add(navPropSurveContext);

                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_ORCHESTRATION_CONTEXT_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, contextQueue, userId, channelProviderId,
                                                        channelProviderAddress));
                        entityType.setKey(Collections.singletonList(propertyRef));
                        entityType.setNavigationProperties(navPropList);

                        return entityType;
                } else if (entityTypeName.equals(ET_ATTRIBUTE_LIST_FQN)) {

                        // create EntityType properties
                        CsdlProperty id = new CsdlProperty().setName("Id")
                                        .setType(EdmPrimitiveTypeKind.Guid.getFullQualifiedName());
                        CsdlProperty attributeValue = new CsdlProperty().setName("AttributeValue")
                                        .setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

                        // create CsdlPropertyRef for Key element
                        CsdlPropertyRef propertyRef = new CsdlPropertyRef();
                        propertyRef.setName("Id");
                        // navigation property: many-to-one, null not allowed (AttributeList must have a
                        // OrchestrationContext)
                        CsdlNavigationProperty navProp = new CsdlNavigationProperty()
                                        .setName(ET_ORCHESTRATION_CONTEXT_NAME) // OrchestrationContext
                                        .setType(ET_ORCHESTRATION_CONTEXT_FQN).setNullable(false)
                                        .setPartner(ES_ATTRIBUTE_LIST_NAME); // AttributeLists

                        List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
                        navPropList.add(navProp);
                        // configure EntityType
                        CsdlEntityType entityType = new CsdlEntityType();
                        entityType.setName(ET_ATTRIBUTE_LIST_NAME);
                        entityType.setProperties(
                                        Arrays.asList(id, attributeValue));
                        entityType.setKey(Collections.singletonList(propertyRef));
                        entityType.setNavigationProperties(navPropList);
                        return entityType;
                }

                return null;
        }

        // getSchemas() The Schema is the root element to carry the elements.
        @Override
        public List<CsdlSchema> getSchemas() throws ODataException {
                // create Schema
                CsdlSchema schema = new CsdlSchema();
                schema.setNamespace(NAMESPACE);

                // add EntityTypes
                List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
                entityTypes.add(getEntityType(ET_CUSTOMER_INFO_FQN));
                entityTypes.add(getEntityType(ET_SURVEY_FQN));
                entityTypes.add(getEntityType(ET_ANSWER_FQN));
                entityTypes.add(getEntityType(ET_QUESTION_FQN));
                entityTypes.add(getEntityType(ET_QUESTION_OPTION_FQN));
                entityTypes.add(getEntityType(ET_SURVEY_CONTEXT_FQN));
                entityTypes.add(getEntityType(ET_AGENT_FQN));
                entityTypes.add(getEntityType(ET_ORCHESTRATION_CONTEXT_FQN));
                entityTypes.add(getEntityType(ET_ATTRIBUTE_LIST_FQN));
                schema.setEntityTypes(entityTypes);

                // add EntityContainer
                schema.setEntityContainer(getEntityContainer());

                // finally
                List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
                schemas.add(schema);

                return schemas;
        }

}
