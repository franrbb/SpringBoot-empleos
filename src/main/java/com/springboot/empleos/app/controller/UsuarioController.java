package com.springboot.empleos.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.empleos.app.models.entity.Perfil;
import com.springboot.empleos.app.models.entity.Usuario;
import com.springboot.empleos.app.models.service.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/listaUsuarios")
	public String listaUsuarios(Model model) {
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("usuarios", usuarioService.listaUsuarios());
		
		return "usuarios/listaUsuarios";
	}
	
	@GetMapping("/formRegistro")
	public String formGuardar(Model model) {
		
		Usuario usuario = new Usuario();
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("usuario", usuario);
		
		return "usuarios/formRegistro";
	}
	
	@PostMapping("/formRegistro")
	public String save(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash, Model model) {
		
		if(result.hasErrors()) {
			return "usuarios/formRegistro";
		}
		
		usuario.setEstatus(1);
		// Recuperamos el password en texto plano
		String pwdPlano = usuario.getPassword();
		// Encriptamos el pwd BCryptPasswordEncoder
		String pwdEncriptado = passwordEncoder.encode(pwdPlano); 
		// Hacemos un set al atributo password (ya viene encriptado)
		usuario.setPassword(pwdEncriptado);
		
		// Creamos el Perfil que le asignaremos al usuario nuevo
		Perfil perfil = new Perfil();
		perfil.setId(3L); // Perfil USUARIO
		usuario.agregar(perfil);

		usuarioService.save(usuario);
		flash.addFlashAttribute("success", "Usuario registrado con éxito");
		
		return "redirect:/";
	}

}
