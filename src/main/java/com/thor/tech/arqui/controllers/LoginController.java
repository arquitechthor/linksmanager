package com.thor.tech.arqui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

	@GetMapping(path = {"/inicio","/",""})
	public String Inicio() {
		return "public/login";
	}
}
