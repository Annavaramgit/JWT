package com.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.custom_exceptions.BadRequestCls;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
/*this class executes each request*/
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MyUserDetailasService myUserDetailasService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    try {
	        log.info("doFilter method entered...");
	        String header = request.getHeader("Authorization"); // Changed from "Authentication" to "Authorization"

	        // if req header is null or its not start's with Bearer then ignore it
	        if (header == null || !header.startsWith("Bearer ")) {
	            log.info("doFilter method if block entered...");
	            filterChain.doFilter(request, response);
	            return;
	        }

	        String token = header.substring(7);
	        String userName = jwtService.getSubjectFromToken(token);

	        // Check if userName is null and if already authenticated
	        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userInfo = myUserDetailasService.loadUserByUsername(userName);

	            if (userInfo != null && jwtService.isTokenValid(token)) {
	                // Create UsernamePasswordAuthenticationToken
	                UsernamePasswordAuthenticationToken authenticationToken =
	                        new UsernamePasswordAuthenticationToken(
	                                userInfo,
	                                null,
	                                userInfo.getAuthorities());

	                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                // Mark the context as logged in
	                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	            }
	        }
	        filterChain.doFilter(request, response);

	    } catch (Exception e) {
	        log.info("Error in JwtAuthenticationFilter: " + e.toString());
	        throw new BadRequestCls("Bad request: " + e.getMessage());
	    }
	}

}
