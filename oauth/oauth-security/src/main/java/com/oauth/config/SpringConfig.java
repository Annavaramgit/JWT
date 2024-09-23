package com.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringConfig {
	
	@Bean
	public SecurityFilterChain config(HttpSecurity http)throws Exception {
		return http.authorizeHttpRequests(req->{
			req.requestMatchers("/").permitAll();
			req.anyRequest().authenticated();
		})
				.oauth2Login(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.build();
		
	}

}
