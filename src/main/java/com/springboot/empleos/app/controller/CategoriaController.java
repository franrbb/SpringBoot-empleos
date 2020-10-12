package com.springboot.empleos.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.empleos.app.models.entity.Categoria;
import com.springboot.empleos.app.models.service.ICategoriaService;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@GetMapping("/listaCategorias")
	public String listaCategorias(Model model) {
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("categorias", categoriaService.listaCategorias());
		
		return "categorias/listaCategorias";
	}
	
	@GetMapping("/formCategoria")
	public String form(Model model) {
		
		Categoria categoria = new Categoria();
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}
	
	@PostMapping("/formCategoria")
	public String save(Categoria categoria, Model model) {
		
		categoriaService.save(categoria);
		
		return "redirect:/categorias/listaCategorias";
	}

}
