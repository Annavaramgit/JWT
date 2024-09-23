package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private MyUserDetailasService userDetailasService;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(req -> req.requestMatchers("/saveData","/authenticate").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				        .formLogin()
				          // Use the default Spring Security login page
				        .and()
				        .logout(logout -> logout.permitAll()) // Allow everyone to see the logout page
				        .build();

	}

	/* password encoder for encode the password */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/* userdetailsservice for fetch user info from db */
	@Bean
	public UserDetailsService userDetailasService() {
		return userDetailasService;
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailasService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/*AuthenticationManager help to authenticate user by username & password*/
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}

}
