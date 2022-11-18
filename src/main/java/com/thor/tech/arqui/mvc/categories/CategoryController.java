package com.thor.tech.arqui.mvc.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryRepo repo;
	
	@GetMapping(path = {"/list","/",""})
	public String List(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		
		model.addAttribute("lista",repo.findAllByUsername(usrname));
		model.addAttribute("conteo",repo.count());
		return "admin/categories/list";
	}
	
	@GetMapping(path = {"/add"})
	public String getForm(Model model) {
		model.addAttribute("modelo", new Category());
		return "admin/categories/add";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		Category modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
	    model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "edit-exito");
	    model.addAttribute("conteo",repo.count());
		return "admin/categories/edit";
	}
	
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
		Category modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
	    model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "view-exito");
	    model.addAttribute("conteo",repo.count());
		return "admin/categories/view";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		Category modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
		repo.delete(modelo);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "delete-exito");
	    model.addAttribute("lista",repo.findAllByUsername(usrname));
	    model.addAttribute("conteo",repo.count());
	    return "admin/categories/list";
	}
	
	@PostMapping(path = {"/save"})
	public String Add(Category nuevo, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		nuevo.setUsername(usrname);
		repo.save(nuevo);
		model.addAttribute("lista",repo.findAllByUsername(usrname));
		model.addAttribute("modelo", nuevo);
		model.addAttribute("mensaje", "add-exito");
		model.addAttribute("conteo",repo.count());
		return "admin/categories/list";
	}
}
