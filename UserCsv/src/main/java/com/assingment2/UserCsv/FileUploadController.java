package com.assingment2.UserCsv;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private ProducerTemplate producerTemplate;
    

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        // Use Camel to send the file to the Camel route for processing
        producerTemplate.sendBody("direct:processCsv", file);

        return ResponseEntity.ok("File uploaded successfully");
    }

    @Autowired
    private CamelContext camelContext;

    @GetMapping("/users")
    public ResponseEntity<String> getAllUsers() {
        // Use Camel to invoke the direct:getAllUsers route
        String result = camelContext.createProducerTemplate().requestBody("direct:getAllUsers", null, String.class);

        return ResponseEntity.ok(result);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        // Use Camel to invoke the direct:deleteUserById route
        String result = camelContext.createProducerTemplate().requestBody("direct:deleteUserById", id, String.class);

        return ResponseEntity.ok("record deleted successfully for userId :" +result);
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllCsvData(@RequestParam("file") MultipartFile file) {
    	// Use Camel to invoke the direct:deleteCsvData route
        try {           
            // Create a ProducerTemplate using CamelContext
            ProducerTemplate producerTemplate = camelContext.createProducerTemplate();

            // Send the content of the file to the Camel route
            producerTemplate.sendBody("direct:deleteCsvData", file);
           // producerTemplate.sendBody("direct:deleteCsvData", new String(fileContent));

            // Close the ProducerTemplate
            producerTemplate.stop();

            return ResponseEntity.ok("CSV data processed successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing CSV data");
        }
    }
}
