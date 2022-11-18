package com.thor.tech.arqui.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@GetMapping(path = {"/inicio","/",""})
	public String Inicio(HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User usr = (User) auth.getPrincipal();
		session.setAttribute("logged",usr.getUsername());
		;
		return "admin/index";
	}
	
	@GetMapping(path = {"/login"})
	public String Login() {
		return "public/login";
	}
}
