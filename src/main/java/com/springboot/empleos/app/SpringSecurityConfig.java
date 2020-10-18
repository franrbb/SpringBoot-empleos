package com.springboot.empleos.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springboot.empleos.app.auth.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private DataSource dataSource;
	
	/**
	 * Personalizamos el Acceso a las URLs de la aplicación
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() 
    	
    	//Los recursos estáticos no requieren autenticación
        .antMatchers(
                "/css/**",
                "/images/**",
                "/js/**",
                "/uploads/**").permitAll()
        
        //Las vistas públicas no requieren autenticación
        .antMatchers(
        		"/",
        		"/detalle/**",
        		"/usuarios/**").permitAll()
     
        //Asignar permisos a URLs por ROLES        
        .antMatchers("/vacantes/**").hasAnyAuthority("ADMINISTRADOR")
        .antMatchers("/categorias/**").hasAnyAuthority("ADMINISTRADOR")
        
        //Todas las demás URLs de la Aplicación requieren autenticación
        .anyRequest().authenticated()
		
		//El formulario de Login no requiere autenticacion
        .and().formLogin().successHandler(successHandler).loginPage("/login").permitAll()        
        .and().logout().permitAll()
        .and().exceptionHandling().accessDeniedPage("/error403");
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
		
		/*PasswordEncoder encoder = passwordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("1234").roles("ADMIN", "USER"))
		.withUser(users.username("keko").password("1234").roles("USER"));*/
		
		builder.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("select username, password, estatus from Usuarios where username=?")
		.authoritiesByUsernameQuery("select u.username, p.perfil from UsuarioPerfil up " + 
			"inner join Usuarios u on u.id = up.idUsuario " + 
			"inner join Perfiles p on p.id = up.idPerfil " + 
			"where u.username = ?");
		
	}

}
