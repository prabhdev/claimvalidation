package com.submitter.val.config;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger configuration for patient validation
 * 
 * @author DPrabhu
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${version}")
    private String version;

	/**
	 * Build docket for swagger page
	 * 
	 * @return
	 */
    @Bean
    public Docket api() {
        Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.submitter.val"))
                .build();

        HashSet<String> protocols = new HashSet<String>();
        protocols.add("http");
        docket.protocols(protocols);

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Submitter Validation Service API").description("Reference for developers")
                .version(version).build();
    }


}
