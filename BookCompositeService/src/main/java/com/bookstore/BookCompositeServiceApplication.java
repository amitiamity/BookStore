package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BookCompositeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookCompositeServiceApplication.class, args);
	}
}
