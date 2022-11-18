package com.thor.tech.arqui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PublicController {

	@GetMapping(path = {"/","","/inicio"})
	public String Inicio() {
		return "public/index";
	}
	
	@GetMapping(path = {"/security/login"})
	public String Login() {
		return "public/login";
	}
	
	@GetMapping(path = {"/error"})
	public String Error() {
		return "public/error";
	}
	
	@GetMapping(path = {"/auth-error"})
	public String AuthError(Model m) {
		m.addAttribute("mensaje", "auth-error");
		return "public/login";
	}
}
