package com.thor.tech.arqui.mvc.users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository <UserEntity,Integer>{
	UserEntity findByUsername (String username);
}
