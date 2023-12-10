package com.assingment.Assingment1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.assignment")
public class Assingment1Application {

	public static void main(String[] args) {
		SpringApplication.run(Assingment1Application.class, args);
	}

}
    