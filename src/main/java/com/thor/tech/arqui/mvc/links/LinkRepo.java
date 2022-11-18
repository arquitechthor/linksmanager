package com.thor.tech.arqui.mvc.links;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thor.tech.arqui.mvc.categories.Category;

public interface LinkRepo extends JpaRepository <Link, Integer> {
	public List<Link> findAllByCategory(Category category);

	public List<Link> findAllByCategoryAndUsername(Category cat, String username);

	public List<Link> findAllByUsername(String usrname);
}
