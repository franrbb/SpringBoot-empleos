package com.springboot.empleos.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.empleos.app.models.entity.Categoria;
import com.springboot.empleos.app.models.service.ICategoriaService;
import com.springboot.empleos.app.validation.CategoriaValidador;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private CategoriaValidador validador;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(validador);
	}
	
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
	public String save(@Valid Categoria categoria, BindingResult result, Model model) {
		
		//validador.validate(categoria, result);
		
		if(result.hasErrors()) {
			return "categorias/formCategoria";
		}
		
		categoriaService.save(categoria);
		
		return "redirect:/categorias/listaCategorias";
	}

}
