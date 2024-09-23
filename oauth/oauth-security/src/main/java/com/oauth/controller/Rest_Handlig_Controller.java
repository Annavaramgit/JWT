package com.oauth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Rest_Handlig_Controller {
	@GetMapping("/")
	public String welcomeMethod() {
		System.out.println("welcome method ");
		return "Welcome To My Application!!!!";
	}

	@RequestMapping("/user")
	public Principal getLoginUserDetails(Principal user) {
		System.out.println("login user details  method ");
		return user;
	}
	@GetMapping("/sample")
	public String sampleMethod() {
		return "sample method";
	}
}
