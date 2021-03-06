/**
 * 
 */
package com.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author amitpatel
 *
 */
@Configuration
public class Config {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
