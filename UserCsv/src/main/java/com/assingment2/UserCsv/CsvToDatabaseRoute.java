package com.assingment2.UserCsv;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


	
	@Component
	public class CsvToDatabaseRoute extends RouteBuilder {

		  @Override
		    public void configure() throws Exception {
			  //to insert bulk user
		        from("direct:processCsv")
		        .process(this::extractInputStream)
		            .log("Received file: ${header.CamelFileName}")
		            .unmarshal().csv()
		            .split(body())
		            .to("sql:INSERT INTO user_master (username, password) VALUES (#, #)?dataSource=#dataSource")
		            .log("Inserted user ${body}");
		        
		        // to get all user
		        from("direct:getAllUsers")
	            .to("sql:SELECT * FROM user_master?dataSource=#dataSource")
	            .log("Retrieved all users: ${body}");
		        
		     // Camel route to delete a user by ID
		        from("direct:deleteUserById")
		            .routeId("deleteUserByIdRoute")
		            .to("sql:DELETE FROM user_master WHERE id = :#${body}?dataSource=#dataSource")
		            .log("Deleted user with ID: ${body}");
		        
		     // Route to delete data from CSV file
//		        from("file:D:/?fileName=user_Details_2.csv")
//		        .routeId("uploadCsvRoute")
//		        .log("Uploading CSV file: ${header.CamelFileName}")
//		        .to("direct:deleteCsvData")
//		        .to("file:D:/?fileName=user_Details_2.csv") // Overwrite the same file
//		        .log("Data deleted from and saved back to CSV file: ${header.CamelFileName}");

		        from("direct:deleteCsvData")
	            .routeId("deleteCsvDataRoute")
	            .process(this::extractInputStream)
	            .log("Deleting data from CSV file: ${header.CamelFileName}")
	            .convertBodyTo(String.class) // Ensure the body is a string
	            .process(this::deleteCsvData)
	            .to("file:D:/?fileName=${header.CamelFileName}") // Overwrite the same file
	            .log("Data saved back to CSV file: ${header.CamelFileName}");		    

		    }
		  private void extractInputStream(Exchange exchange) {
			  org.apache.camel.Message in = exchange.getIn();
		        Object body = in.getBody();
		        
		        if (body instanceof InputStream) {
		            // Already an InputStream, nothing to do
		            return;
		        } else if (body instanceof org.springframework.web.multipart.MultipartFile) {
		            // Convert MultipartFile to InputStream
		            try {
		                InputStream inputStream = ((org.springframework.web.multipart.MultipartFile) body).getInputStream();
		                in.setBody(inputStream);
		                
		                MultipartFile multipartFile = (MultipartFile) body;
		             // Set filename as the CamelFileName header
		                String originalFilename = multipartFile.getOriginalFilename();
		                in.setHeader("CamelFileName", originalFilename);
		            } catch (Exception e) {
		                // Handle exception (log, throw, etc.)
		                e.printStackTrace();
		            }
		        } else {
		            // Handle other types if needed
		            throw new RuntimeException("Unsupported message body type: " + body.getClass());
		        }
		    }
		  
		  private void deleteCsvData(Exchange exchange) {
		        String originalContent = exchange.getIn().getBody(String.class);

		        // Implement your logic to delete data from the CSV file
		        // For example, you can set the content to an empty string
		        String modifiedContent = "";

		        exchange.getIn().setBody(modifiedContent);
		    }

		  
	}
	



