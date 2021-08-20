package io.gateway.validationdiscoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Service discovery handled by enabling EurekaServer. Information of Submitter
 * validation service and patient validation service gets registered.
 * 
 * @author Devanadha Prabhu
 */

@SpringBootApplication
@EnableEurekaServer
public class ValidationDiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidationDiscoveryServerApplication.class, args);
	}

}
