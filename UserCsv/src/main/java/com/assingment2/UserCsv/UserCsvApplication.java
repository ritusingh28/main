package com.assingment2.UserCsv;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class UserCsvApplication {
	//@Autowired
   // private CamelContext camelContext;

	public static void main(String[] args) {
		SpringApplication.run(UserCsvApplication.class, args);
	}

}
