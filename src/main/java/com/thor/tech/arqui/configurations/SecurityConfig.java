package com.thor.tech.arqui.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thor.tech.arqui.mvc.users.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService user;
	
	private BCryptPasswordEncoder passEncoder;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	private String[] allowedPaths= new String[] {"/","/users/register","/users/savepublic","/login","/security/login","/css/**","/images/**","/js/**","/bucket-ico-fonts/**","/bs3/**","/font-awesome/**"};   
	
	/*@Autowired
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder password = new BCryptPasswordEncoder();
		return password;
	} */

	@Override
	protected void configure (AuthenticationManagerBuilder auth) throws Exception  {
		auth.userDetailsService(user).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure (HttpSecurity http) throws Exception{

		http
		.authorizeRequests().antMatchers(allowedPaths).permitAll().and()
		.authorizeRequests().anyRequest().authenticated().and()
		.formLogin().loginPage("/security/login").permitAll()
		.defaultSuccessUrl("/admin/inicio").failureUrl("/auth-error")
			.usernameParameter("username").passwordParameter("password").and()
		.logout().logoutUrl("/logout").permitAll().logoutSuccessUrl("/")
		.and().rememberMe().key("uniqueAndSecret");
		
	}
}
