package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AddServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddServiceApplication.class, args);
	}
}
