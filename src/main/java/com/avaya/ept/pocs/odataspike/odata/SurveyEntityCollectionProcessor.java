package com.avaya.ept.pocs.odataspike.odata;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.olingo.commons.api.Constants;
import org.apache.olingo.commons.api.data.ContextURL;
import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Link;
import org.apache.olingo.commons.api.edm.EdmElement;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmNavigationProperty;
import org.apache.olingo.commons.api.edm.EdmNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.apache.olingo.commons.api.http.HttpHeader;
import org.apache.olingo.commons.api.http.HttpStatusCode;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataApplicationException;
import org.apache.olingo.server.api.ODataRequest;
import org.apache.olingo.server.api.ODataResponse;
import org.apache.olingo.server.api.ServiceMetadata;
import org.apache.olingo.server.api.processor.EntityCollectionProcessor;
import org.apache.olingo.server.api.serializer.EntityCollectionSerializerOptions;
import org.apache.olingo.server.api.serializer.ODataSerializer;
import org.apache.olingo.server.api.serializer.SerializerException;
import org.apache.olingo.server.api.serializer.SerializerResult;
import org.apache.olingo.server.api.uri.UriInfo;
import org.apache.olingo.server.api.uri.UriInfoResource;
import org.apache.olingo.server.api.uri.UriParameter;
import org.apache.olingo.server.api.uri.UriResource;
import org.apache.olingo.server.api.uri.UriResourceEntitySet;
import org.apache.olingo.server.api.uri.UriResourceNavigation;
import org.apache.olingo.server.api.uri.UriResourcePrimitiveProperty;
import org.apache.olingo.server.api.uri.queryoption.CountOption;
import org.apache.olingo.server.api.uri.queryoption.ExpandItem;
import org.apache.olingo.server.api.uri.queryoption.ExpandOption;
import org.apache.olingo.server.api.uri.queryoption.FilterOption;
import org.apache.olingo.server.api.uri.queryoption.OrderByItem;
import org.apache.olingo.server.api.uri.queryoption.OrderByOption;
import org.apache.olingo.server.api.uri.queryoption.SelectOption;
import org.apache.olingo.server.api.uri.queryoption.SkipOption;
import org.apache.olingo.server.api.uri.queryoption.TopOption;
import org.apache.olingo.server.api.uri.queryoption.expression.Expression;
import org.apache.olingo.server.api.uri.queryoption.expression.ExpressionVisitException;
import org.apache.olingo.server.api.uri.queryoption.expression.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SurveyEntityCollectionProcessor implements EntityCollectionProcessor {

    @Autowired
    StorageService storageService;

    private OData odata;
    private ServiceMetadata serviceMetadata;

    // our processor is initialized with the OData context object
    public void init(OData odata, ServiceMetadata serviceMetadata) {
        this.odata = odata;
        this.serviceMetadata = serviceMetadata;
    }

    // the only method that is declared in the EntityCollectionProcessor interface
    // this method is called, when the user fires a request to an EntitySet
    // in our example, the URL would be:
    // http://localhost:3000/OData/V4.0/CustomerInfos
    public void readEntityCollection(ODataRequest request, ODataResponse response,
            UriInfo uriInfo, ContentType responseFormat)
            throws ODataApplicationException, SerializerException {

        EdmEntitySet responseEdmEntitySet = null; // we'll need this to build the ContextURL
        EntityCollection responseEntityCollection = null; // we'll need this to set the response body
        EdmEntityType responseEdmEntityType = null;

        // 1st retrieve the requested EntitySet from the uriInfo (representation of the
        // parsed URI)
        List<UriResource> resourceParts = uriInfo.getUriResourceParts();
        int segmentCount = resourceParts.size();

        UriResource uriResource = resourceParts.get(0); // in our example, the first segment is the EntitySet
        if (!(uriResource instanceof UriResourceEntitySet)) {
            throw new ODataApplicationException("Only EntitySet is supported",
                    HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        UriResourceEntitySet uriResourceEntitySet = (UriResourceEntitySet) uriResource;
        EdmEntitySet startEdmEntitySet = uriResourceEntitySet.getEntitySet();

        if (segmentCount == 1) { // this is the case for: DemoService/DemoService.svc/Categories
            responseEdmEntitySet = startEdmEntitySet; // the response body is built from the first (and only) entitySet

            // 2nd: fetch the data from backend for this requested EntitySetName and deliver
            // as EntitySet
            responseEntityCollection = storageService.readEntitySetData(startEdmEntitySet);
        } else if (segmentCount == 2) { // in case of navigation: DemoService.svc/Categories(3)/Products

            UriResource lastSegment = resourceParts.get(1); // in our example we don't support more complex URIs
            if (lastSegment instanceof UriResourceNavigation) {
                UriResourceNavigation uriResourceNavigation = (UriResourceNavigation) lastSegment;
                EdmNavigationProperty edmNavigationProperty = uriResourceNavigation.getProperty();
                EdmEntityType targetEntityType = edmNavigationProperty.getType();
                if (!edmNavigationProperty.containsTarget()) {
                    // from Categories(1) to Products
                    responseEdmEntitySet = Util.getNavigationTargetEntitySet(startEdmEntitySet, edmNavigationProperty);
                } else {
                    responseEdmEntitySet = startEdmEntitySet;
                    responseEdmEntityType = targetEntityType;
                }

                // 2nd: fetch the data from backend
                // first fetch the entity where the first segment of the URI points to
                List<UriParameter> keyPredicates = uriResourceEntitySet.getKeyPredicates();
                // e.g. for Categories(3)/Products we have to find the single entity: Category
                // with ID 3
                Entity sourceEntity = storageService.readEntityData(startEdmEntitySet, keyPredicates);
                // error handling for e.g. DemoService.svc/Categories(99)/Products
                if (sourceEntity == null) {
                    throw new ODataApplicationException("Entity not found.",
                            HttpStatusCode.NOT_FOUND.getStatusCode(), Locale.ROOT);
                }
                // then fetch the entity collection where the entity navigates to
                // note: we don't need to check uriResourceNavigation.isCollection(),
                // because we are the EntityCollectionProcessor
                responseEntityCollection = storageService.getRelatedEntityCollection(sourceEntity, targetEntityType);
            }
        } else { // this would be the case for e.g. Products(1)/Category/Products
            throw new ODataApplicationException("Not supported",
                    HttpStatusCode.NOT_IMPLEMENTED.getStatusCode(), Locale.ROOT);
        }

        List<Entity> entityList = responseEntityCollection.getEntities();
        EntityCollection returnEntityCollection = new EntityCollection();
        // Queries
        // apply system query options
        SelectOption selectOption = uriInfo.getSelectOption();
        ExpandOption expandOption = uriInfo.getExpandOption();

        // handle $expand
        // in our example:
        // http://localhost:8080/DemoService/DemoService.svc/Categories/$expand=Products
        // or
        // http://localhost:8080/DemoService/DemoService.svc/Products?$expand=Category
        if (expandOption != null) {
            // retrieve the EdmNavigationProperty from the expand expression
            // Note: in our example, we have only one NavigationProperty, so we can directly
            // access it
            EdmNavigationProperty edmNavigationProperty = null;
            ExpandItem expandItem = expandOption.getExpandItems().get(0);
            if (expandItem.isStar()) {
                List<EdmNavigationPropertyBinding> bindings = startEdmEntitySet.getNavigationPropertyBindings();
                // we know that there are navigation bindings
                // however normally in this case a check if navigation bindings exists is done
                if (!bindings.isEmpty()) {
                    // can in our case only be 'Category' or 'Products', so we can take the first
                    EdmNavigationPropertyBinding binding = bindings.get(0);
                    EdmElement property = startEdmEntitySet.getEntityType().getProperty(binding.getPath());
                    // we don't need to handle error cases, as it is done in the Olingo library
                    if (property instanceof EdmNavigationProperty) {
                        edmNavigationProperty = (EdmNavigationProperty) property;
                    }
                }
            } else {
                // can be 'Category' or 'Products', no path supported
                uriResource = expandItem.getResourcePath().getUriResourceParts().get(0);
                // we don't need to handle error cases, as it is done in the Olingo library
                if (uriResource instanceof UriResourceNavigation) {
                    edmNavigationProperty = ((UriResourceNavigation) uriResource).getProperty();
                }
            }

            // can be 'Category' or 'Products', no path supported
            // we don't need to handle error cases, as it is done in the Olingo library
            if (edmNavigationProperty != null) {
                String navPropName = edmNavigationProperty.getName();
                EdmEntityType expandEdmEntityType = edmNavigationProperty.getType();

                for (Entity entity : entityList) {
                    Link link = new Link();
                    link.setTitle(navPropName);
                    link.setType(Constants.ENTITY_NAVIGATION_LINK_TYPE);
                    link.setRel(Constants.NS_ASSOCIATION_LINK_REL + navPropName);

                    if (edmNavigationProperty.isCollection()) { // in case of Categories/$expand=Products
                        // fetch the data for the $expand (to-many navigation) from backend
                        EntityCollection expandEntityCollection = storageService.getRelatedEntityCollection(entity,
                                expandEdmEntityType);
                        link.setInlineEntitySet(expandEntityCollection);
                        link.setHref(expandEntityCollection.getId().toASCIIString());
                    } else { // in case of Products?$expand=Category
                        // fetch the data for the $expand (to-one navigation) from backend
                        // here we get the data for the expand
                        Entity expandEntity = storageService.getRelatedEntity(entity, expandEdmEntityType);
                        link.setInlineEntity(expandEntity);
                        link.setHref(expandEntity.getId().toASCIIString());
                    }

                    // set the link - containing the expanded data - to the current entity
                    entity.getNavigationLinks().add(link);
                }
            }
        }

        // Query $orderby
        OrderByOption orderByOption = uriInfo.getOrderByOption();
        if (orderByOption != null) {
            List<OrderByItem> orderItemList = orderByOption.getOrders();
            final OrderByItem orderByItem = orderItemList.get(0); // in our example we support only one
            Expression expression = orderByItem.getExpression();
            if (expression instanceof Member) {
                UriInfoResource resourcePath = ((Member) expression).getResourcePath();
                uriResource = resourcePath.getUriResourceParts().get(0);
                if (uriResource instanceof UriResourcePrimitiveProperty) {
                    EdmProperty edmProperty = ((UriResourcePrimitiveProperty) uriResource).getProperty();
                    final String sortPropertyName = edmProperty.getName();

                    // do the sorting for the list of entities
                    Collections.sort(entityList, new Comparator<Entity>() {

                        // we delegate the sorting to the native sorter of Integer and String
                        public int compare(Entity entity1, Entity entity2) {
                            int compareResult = 0;

                            if (sortPropertyName.equals("ID")) {
                                Integer integer1 = (Integer) entity1.getProperty(sortPropertyName).getValue();
                                Integer integer2 = (Integer) entity2.getProperty(sortPropertyName).getValue();

                                compareResult = integer1.compareTo(integer2);
                            } else {
                                String propertyValue1 = (String) entity1.getProperty(sortPropertyName).getValue();
                                String propertyValue2 = (String) entity2.getProperty(sortPropertyName).getValue();

                                compareResult = propertyValue1.compareTo(propertyValue2);
                            }

                            // if 'desc' is specified in the URI, change the order of the list
                            if (orderByItem.isDescending()) {
                                return -compareResult; // just convert the result to negative value to change the order
                            }

                            return compareResult;
                        }
                    });
                }
            }
        }

        // 3rd: Check if filter system query option is provided and apply the expression
        // if necessary
        FilterOption filterOption = uriInfo.getFilterOption();
        if (filterOption != null) {
            // Apply $filter system query option
            try {
                entityList = responseEntityCollection.getEntities();
                Iterator<Entity> entityIterator = entityList.iterator();

                // Evaluate the expression for each entity
                // If the expression is evaluated to "true", keep the entity otherwise remove it
                // from the entityList
                while (entityIterator.hasNext()) {
                    // To evaluate the the expression, create an instance of the Filter Expression
                    // Visitor and pass
                    // the current entity to the constructor
                    Entity currentEntity = entityIterator.next();
                    Expression filterExpression = filterOption.getExpression();
                    FilterExpressionVisitor expressionVisitor = new FilterExpressionVisitor(currentEntity);

                    // Start evaluating the expression
                    Object visitorResult = filterExpression.accept(expressionVisitor);

                    // The result of the filter expression must be of type Edm.Boolean
                    if (visitorResult instanceof Boolean) {
                        if (!Boolean.TRUE.equals(visitorResult)) {
                            // The expression evaluated to false (or null), so we have to remove the
                            // currentEntity from entityList
                            entityIterator.remove();
                        }
                    } else {
                        throw new ODataApplicationException("A filter expression must evaulate to type Edm.Boolean",
                                HttpStatusCode.BAD_REQUEST.getStatusCode(), Locale.ENGLISH);
                    }
                }

            } catch (ExpressionVisitException e) {
                throw new ODataApplicationException("Exception in filter evaluation",
                        HttpStatusCode.INTERNAL_SERVER_ERROR.getStatusCode(), Locale.ENGLISH);
            }
        }

        // filters

        // handle $count: always return the original number of entities, without
        // considering $top and $skip
        CountOption countOption = uriInfo.getCountOption();
        if (countOption != null) {
            boolean isCount = countOption.getValue();
            if (isCount) {
                returnEntityCollection.setCount(entityList.size());
            }
        }

        // handle $skip
        SkipOption skipOption = uriInfo.getSkipOption();
        if (skipOption != null) {
            int skipNumber = skipOption.getValue();
            if (skipNumber >= 0) {
                if (skipNumber <= entityList.size()) {
                    entityList = entityList.subList(skipNumber, entityList.size());
                } else {
                    // The client skipped all entities
                    entityList.clear();
                }
            } else {
                throw new ODataApplicationException("Invalid value for $skip",
                        HttpStatusCode.BAD_REQUEST.getStatusCode(),
                        Locale.ROOT);
            }
        }

        // handle $top
        TopOption topOption = uriInfo.getTopOption();
        if (topOption != null) {
            int topNumber = topOption.getValue();
            if (topNumber >= 0) {
                if (topNumber <= entityList.size()) {
                    entityList = entityList.subList(0, topNumber);
                } // else the client has requested more entities than available => return what we
                  // have
            } else {
                throw new ODataApplicationException("Invalid value for $top",
                        HttpStatusCode.BAD_REQUEST.getStatusCode(),
                        Locale.ROOT);
            }
        }

        ContextURL contextUrl = null;
        // EdmEntityType edmEntityType = null;

        // serialize
        EdmEntityType edmEntityType = startEdmEntitySet.getEntityType();
        // we need the property names of the $select, in order to build the context URL
        String selectList = odata.createUriHelper().buildContextURLSelectList(edmEntityType, expandOption,
                selectOption);
        // 3rd: create and configure a serializer
        if (isContNav(uriInfo)) {
            edmEntityType = responseEdmEntityType;
            contextUrl = ContextURL.with().entitySetOrSingletonOrType(request.getRawODataPath()).selectList(selectList)
                    .build();
        } else {
            edmEntityType = responseEdmEntitySet.getEntityType();
            contextUrl = ContextURL.with().entitySet(responseEdmEntitySet).selectList(selectList).build();
        }

        final String id = request.getRawBaseUri() + "/" + responseEdmEntitySet.getName();

        // after applying the query options, create EntityCollection based on the
        // reduced list
        for (Entity entity : entityList) {
            returnEntityCollection.getEntities().add(entity);
        }

        // enable $count filter
        // make sure that $expand and $select are considered by the serializer
        // adding the selectOption to the serializerOpts will actually tell the lib to
        // do the job
        EntityCollectionSerializerOptions opts = EntityCollectionSerializerOptions.with()
                .contextURL(contextUrl).id(id).select(selectOption)
                .expand(expandOption).count(countOption).build();
        ODataSerializer serializer = odata.createSerializer(responseFormat);

        SerializerResult serializerResult = serializer.entityCollection(this.serviceMetadata, edmEntityType,
                returnEntityCollection, opts);

        // 4th: configure the response object: set the body, headers and status code
        response.setContent(serializerResult.getContent());
        response.setStatusCode(HttpStatusCode.OK.getStatusCode());
        response.setHeader(HttpHeader.CONTENT_TYPE, responseFormat.toContentTypeString());
    }

    private boolean isContNav(UriInfo uriInfo) {
        List<UriResource> resourceParts = uriInfo.getUriResourceParts();
        for (UriResource resourcePart : resourceParts) {
            if (resourcePart instanceof UriResourceNavigation) {
                UriResourceNavigation navResource = (UriResourceNavigation) resourcePart;
                if (navResource.getProperty().containsTarget()) {
                    return true;
                }
            }
        }
        return false;
    }

}