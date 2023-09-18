package com.avaya.ept.pocs.odataspike.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.apache.olingo.commons.api.edm.provider.CsdlEdmProvider;
import org.apache.olingo.commons.api.edmx.EdmxReference;
import org.apache.olingo.server.api.OData;
import org.apache.olingo.server.api.ODataHttpHandler;
import org.apache.olingo.server.api.ServiceMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avaya.ept.pocs.odataspike.odata.SurveyEntityCollectionProcessor;
import com.avaya.ept.pocs.odataspike.odata.SurveyEntityProcessor;
import com.avaya.ept.pocs.odataspike.odata.SurveyPrimitiveProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Subash
 * @since 2/28/2021
 */
@Slf4j
@RestController
@RequestMapping(ODataController.URI)
public class ODataController {

	protected static final String URI = "/V4.0";

	@Autowired
	CsdlEdmProvider edmProvider;

	@Autowired
	SurveyEntityCollectionProcessor surveyEntityCollectionProcessor;

	@Autowired
	SurveyEntityProcessor surveyEntityProcessor;

	@Autowired
	SurveyPrimitiveProcessor surveyPrimitiveProcessor;

	@RequestMapping(value = "**") // keep two * for full path
	public void process(HttpServletRequest request, HttpServletResponse response) {

		log.info("request: {} ", request.getRequestURI());
		OData odata = OData.newInstance();
		ServiceMetadata edm = odata.createServiceMetadata(edmProvider,
				new ArrayList<EdmxReference>());
		ODataHttpHandler handler = odata.createHandler(edm);
		handler.register(surveyEntityCollectionProcessor);
		handler.register(surveyEntityProcessor);
		handler.register(surveyPrimitiveProcessor);

		handler.process(new HttpServletRequestWrapper(request) {
			// Spring MVC matches the whole path as the servlet path
			// Olingo wants just the prefix, ie upto /OData/V4.0, so that it
			// can parse the rest of it as an OData path. So we need to override
			// getServletPath()
			@Override
			public String getServletPath() {
				return ODataController.URI;
			}
		}, response);
	}
}
