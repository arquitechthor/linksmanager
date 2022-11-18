package com.thor.tech.arqui.mvc.categories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Iterable<Category> findAllByUsername(String username);
	
}
