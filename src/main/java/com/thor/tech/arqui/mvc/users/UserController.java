package com.thor.tech.arqui.mvc.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping(path = {"/list","/",""})
	public String List(Model model) {
		model.addAttribute("users",userRepo.findAll());
		model.addAttribute("countUser",userRepo.count());
		return "admin/users/list";
	}
	
	@GetMapping(path = {"/add"})
	public String getForm(Model model) {
		model.addAttribute("nuevoUsuario", new UserEntity());
		return "admin/users/add";
	}
	
	@GetMapping(path = {"/register"})
	public String getFormRegister(Model model) {
		model.addAttribute("nuevoUsuario", new UserEntity());
		return "admin/users/register";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
	    UserEntity modelo = userRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
	    model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "edit-exito");
	    model.addAttribute("conteo",userRepo.count());
		return "admin/users/edit";
	}
	
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
	    UserEntity modelo = userRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
	    model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "view-exito");
	    model.addAttribute("conteo",userRepo.count());
		return "admin/users/view";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		UserEntity modelo = userRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
		userRepo.delete(modelo);
		model.addAttribute("user", modelo);
	    model.addAttribute("mensaje", "delete-exito");
	    model.addAttribute("users",userRepo.findAll());
	    model.addAttribute("countUser",userRepo.count());
	    return "admin/users/list";
	}
	
	@PostMapping(path = {"/save"})
	public String Add(UserEntity nuevoUsuario, Model model) {
		nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
		nuevoUsuario.setRole("USER");
		userRepo.save(nuevoUsuario);
		model.addAttribute("users",userRepo.findAll());
		model.addAttribute("user", nuevoUsuario);
		model.addAttribute("mensaje", "add-exito");
		model.addAttribute("countUser",userRepo.count());
		return "admin/users/list";
	}
	
	@PostMapping(path = {"/savepublic"})
	public String AddPublic(UserEntity nuevoUsuario, Model model) {
		nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
		nuevoUsuario.setRole("USER");
		userRepo.save(nuevoUsuario);
		model.addAttribute("users",userRepo.findAll());
		model.addAttribute("user", nuevoUsuario);
		model.addAttribute("mensaje", "add-exito");
		model.addAttribute("countUser",userRepo.count());
		return "admin/users/register";
	}
}
