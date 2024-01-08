package com.assingment3.UserGraphql;

	import graphql.GraphQL;
	import graphql.execution.instrumentation.tracing.TracingInstrumentation;
	import graphql.schema.GraphQLSchema;
	import graphql.schema.idl.RuntimeWiring;
	import graphql.schema.idl.SchemaGenerator;
	import graphql.schema.idl.SchemaParser;
	import graphql.schema.idl.TypeDefinitionRegistry;
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;

import com.assingment3.UserGraphql.entity.UserDetail;
import com.assingment3.UserGraphql.resolver.UserDetailResolver;

import java.io.IOException;
	import java.io.InputStreamReader;
import java.util.List;

	@Configuration
	public class GraphQLConfig {

	    @Bean
	    public GraphQL graphQL(GraphQLSchema graphQLSchema) {
	        return GraphQL.newGraphQL(graphQLSchema)
	                .instrumentation(new TracingInstrumentation()) // Use NoOpInstrumentation or TracingInstrumentation
	                .build();
	    }

	    @Bean
	    public GraphQLSchema graphQLSchema(UserDetailResolver userDetailResolver) throws IOException {
	        // Parse the schema file
	        InputStreamReader schemaReader = new InputStreamReader(
	                getClass().getResourceAsStream("/schema.graphqls")
	        );
	        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaReader);

	        // Create the runtime wiring
	        RuntimeWiring runtimeWiring = buildRuntimeWiring(userDetailResolver);

	        // Generate the GraphQL schema
	        SchemaGenerator schemaGenerator = new SchemaGenerator();
	        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);

	        return graphQLSchema;
	    }

	    @Bean
	    public RuntimeWiring buildRuntimeWiring(UserDetailResolver userDetailResolver) {
	        return RuntimeWiring.newRuntimeWiring()
	        		   .type("Query", typeWiring -> typeWiring
	                           .dataFetcher("getAllUserDetails", env -> {
	                               // Ensure that you return List<UserDetail> for getAllUserDetails
	                        	   List<UserDetail> userDetails = userDetailResolver.getAllUserDetails();
	                               return userDetails;
	                           }))
	                .type("Mutation", typeWiring -> typeWiring
	                        .dataFetcher("updateUserDetail", env -> {
	                        	// Parse the ID argument as needed (assuming it's a String)
	                            Long userId = Long.parseLong(env.getArgument("id"));

	                            // Ensure that you return UserDetail for updateUserDetail mutation
	                            return userDetailResolver.updateUserDetail(
	                                userId,
	                                env.getArgument("username"),
	                                env.getArgument("password"),
	                                env.getArgument("mobileNumber"),
	                                env.getArgument("dateOfBirth")
	                            );
	                        })
	                        .dataFetcher("createUserDetail", env -> {
	                            // Ensure that you return UserDetail for createUserDetail mutation
	                            return userDetailResolver.createUserDetail(env.getArgument("username"),
	                                    env.getArgument("password"),
	                                    env.getArgument("mobileNumber"),
	                                    env.getArgument("dateOfBirth"));
	                        })
	                        .dataFetcher("deleteUserDetail", env -> {
	                        	// Parse the ID argument as needed (assuming it's a String)
	                            Long userId = Long.parseLong(env.getArgument("id"));
	                            // Ensure that you return Boolean for deleteUserDetail mutation
	                            return userDetailResolver.deleteUserDetail(userId);
	                        }))
	                .build();
	    }

	}

