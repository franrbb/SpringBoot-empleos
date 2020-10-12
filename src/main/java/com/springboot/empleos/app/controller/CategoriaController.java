package com.springboot.empleos.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.springboot.empleos.app.models.entity.Categoria;
import com.springboot.empleos.app.models.service.ICategoriaService;
import com.springboot.empleos.app.validation.CategoriaValidador;

@Controller
@RequestMapping("/categorias")
@SessionAttributes("categoria")
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
	public String formGuardar(Model model) {
		
		Categoria categoria = new Categoria();
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}
	
	@GetMapping("/formCategoria/{id}")
	public String formEditar(@PathVariable(value = "id")Long id, Model model) {
		
		Categoria categoria = new Categoria();
		
		if(id > 0) {
			categoria = categoriaService.buscarCategoria(id);
		}
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}
	
	@PostMapping("/formCategoria")
	public String save(@Valid Categoria categoria, BindingResult result, SessionStatus status ,Model model) {
		
		//validador.validate(categoria, result);
		
		if(result.hasErrors()) {
			return "categorias/formCategoria";
		}
		
		categoriaService.save(categoria);
		status.setComplete();
		
		return "redirect:/categorias/listaCategorias";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id) {
		
		if(id > 0) {
			categoriaService.delete(id);
		}
		
		return "redirect:/categorias/listaCategorias";
	}

}
