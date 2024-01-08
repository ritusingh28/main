package com.assingment3.UserGraphql.controller;
	
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import graphql.schema.GraphQLSchema;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

	@RestController
	public class GraphQLController  {

	    private final GraphQL graphQL;

	    @Autowired
	    public GraphQLController(GraphQLSchema graphQLSchema) {
	        this.graphQL = GraphQL.newGraphQL(graphQLSchema)
	                .instrumentation(new TracingInstrumentation())
	                .build();
	    }

	    @PostMapping("/graphql")
	    public ResponseEntity<Object> executeGraphQL(@RequestBody String query) {
	        // Execute the GraphQL query
	        ExecutionResult executionResult = graphQL.execute(query);
	        // Extract data from ExecutionResult
	        Object resultData = executionResult.getData();
	        
	        // Convert LinkedHashMap to List<Map<String, Object>> if necessary
	        if (resultData instanceof LinkedHashMap) {
	        	List<Map<String, Object>> dataList = new ArrayList<>((List<Map<String, Object>>) (List<?>) new ArrayList<>(((LinkedHashMap<?, ?>) resultData).values()));

	            resultData = dataList;
	        }


	        return new ResponseEntity<>(resultData, HttpStatus.OK);
	    }
	}



