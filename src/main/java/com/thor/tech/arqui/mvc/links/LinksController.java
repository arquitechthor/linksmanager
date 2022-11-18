package com.thor.tech.arqui.mvc.links;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thor.tech.arqui.mvc.categories.Category;
import com.thor.tech.arqui.mvc.categories.CategoryRepo;

@Controller
@RequestMapping("/links")
public class LinksController {

	@Autowired
	private LinkRepo repo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@GetMapping(path = {"/list","/",""})
	public String list(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		Iterable<Category> categories = categoryRepo.findAllByUsername(usrname);
		
		for (Category cat : categories) {
			List<Link> links = repo.findAllByCategoryAndUsername(cat, usrname);
			cat.setLinks(links);
			System.out.println(cat.getName()+"("+links.size()+"): ");
			for (Link link : links) {
				System.out.println("- "+link.getTitle());
			}
        }
		
		model.addAttribute("categories", categories);
		model.addAttribute("lista",repo.findAllByUsername(usrname));
		model.addAttribute("conteo",repo.count());
		model.addAttribute("conteoCategorias",categoryRepo.count());
		return "admin/links/list";
	}
	
	@GetMapping(path = {"/add"})
	public String getForm(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		
		model.addAttribute("modelo", new Link());
		model.addAttribute("categories", categoryRepo.findAllByUsername(usrname));
		return "admin/links/add";
	}
	
	@GetMapping(path = {"/add-by-category/{id}"})	
	public String getFormWithCategory(@PathVariable("id") int id, Model model) {
		Category cat = categoryRepo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
		Link link = new Link();
		link.setCategory(cat);
		model.addAttribute("category", cat);
		model.addAttribute("modelo", link);
		return "admin/links/addtocat";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") int id, Model model) {
		
		Link modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));	
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String usrname = String.valueOf(auth.getPrincipal().toString().hashCode());
		
	    model.addAttribute("modelo", modelo);	    
	    model.addAttribute("mensaje", "edit-exito");
	    model.addAttribute("conteo",repo.count());
	    model.addAttribute("categories", categoryRepo.findAllByUsername(usrname));
		return "admin/links/edit";
	}
	
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable("id") int id, Model model) {
		Link modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
	    model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "view-exito");
	    model.addAttribute("conteo",repo.count());
		return "admin/links/view";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id, Model model) {
		Link modelo = repo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid Id:" + id));
		repo.delete(modelo);
		model.addAttribute("modelo", modelo);
	    model.addAttribute("mensaje", "delete-exito");
	    return (list(model));
	}
	
	@PostMapping(path = {"/save"})
	public String add(Link nuevo, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		nuevo.setUsername(String.valueOf(auth.getPrincipal().toString().hashCode()));
		repo.save(nuevo);
		model.addAttribute("modelo", nuevo);
		model.addAttribute("mensaje", "add-exito");

		return (list(model));
	}
}
