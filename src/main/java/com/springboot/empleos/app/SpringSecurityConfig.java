package com.springboot.empleos.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	/**
	 * Personalizamos el Acceso a las URLs de la aplicación
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() 
    	
    	//Los recursos estáticos no requieren autenticación
        .antMatchers(
                "/css/**",                        
                "/js/**",
                "/uploads/**").permitAll()
        
     //Las vistas públicas no requieren autenticación
        .antMatchers(
        		"/",
        		"/detalle/**").permitAll()
     
     //Asignar permisos a URLs por ROLES        
        .antMatchers("/vacantes/**").hasAnyRole("ADMIN")
        .antMatchers("/categorias/**").hasAnyRole("ADMIN")
        .antMatchers("/usuarios/**").hasAnyRole("ADMIN")
        
     //Todas las demás URLs de la Aplicación requieren autenticación
        .anyRequest().authenticated();
    }
	
	/**
	 *  Implementación de Spring Security que encripta passwords con el algoritmo Bcrypt
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		UserBuilder users = User.builder();
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("1234").roles("ADMIN", "USER"))
		.withUser(users.username("keko").password("1234").roles("USER"));
		
	}

}
