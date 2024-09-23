package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.config.JwtService;
import com.security.config.MyUserDetailasService;
import com.security.custom_exceptions.UnAuthorizedExceptionCls;
import com.security.dto.Login;
import com.security.entity.Student;
import com.security.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ControllerClass {
	@Autowired
	private StudentService studentService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MyUserDetailasService myUserDetailasService;

	@GetMapping("/getData")
	public String getData() {
		System.out.println("--------------------------");
		return "hello get data ...";
	}

	@GetMapping("/admin/home")
	public String getData1() {
		System.out.println("--------------------------");
		return "Admin home page ...";
	}

	@PostMapping("/saveData")
	public ResponseEntity<String> saveMethod(@RequestBody Student student) {
		student.setPassword(encoder.encode(student.getPassword()));
		return new ResponseEntity<String>(studentService.saveData(student), HttpStatusCode.valueOf(201));
	}

	/* this authenticate user first after it genarates jwt_token */
	@PostMapping("/authenticate")
	public String authenticateUser(@RequestBody Login login) {
		//log.info("entered...");
		try {
		/* Authenticate the user here */
		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(login.getStudentName(), login.getPassword()));
		
		
		
		/*
		 * if authenticate is true then we called here token genaration method for token
		 */
		if (authenticate.isAuthenticated()) {
			log.info("if block");
			return jwtService.genarateJwtToken(myUserDetailasService.loadUserByUsername(login.getStudentName()));

		}
		
		/* if not valid throw exception */
		else {
			log.info("else block");
			throw new UnAuthorizedExceptionCls("Invalid Credintials!!!");
		}
		}catch (AuthenticationException ex) {
			log.info("cath block");
			throw new UnAuthorizedExceptionCls("Authentication failed: " + ex.getMessage());
		}
	}

}
