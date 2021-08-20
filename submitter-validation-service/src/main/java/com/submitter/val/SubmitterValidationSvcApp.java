package com.submitter.val;

/**
 * Microservice for submitter ID validation
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SubmitterValidationSvcApp {

	@Value("${client.timeout}")
	private int timeout;

	/**
	 * RestTemplate added to classpath for external service calls (TODO) 
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(timeout);
		return new RestTemplate(requestFactory);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SubmitterValidationSvcApp.class, args);
	}

}
