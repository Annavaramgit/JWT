package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class TraditionalControllerClass {

	@GetMapping("/login")
	public String login() {
		log.info("login entered");
		return "custom_login"; // This should match the name of your HTML file (login.html)
	}
}
