package com.patient.val.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Configure web security for patient validation service
 * @author Devanadha Prabhu
 *
 */
@Configuration
public class APISecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Configure basic http security
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
	}

	/**
	 * Setup in memory users and roles
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password("{noop}mypassword").roles("ADMIN").and()
				.withUser("apiuser").password("{noop}myapipassword").roles("APIUSER");
	}
}
