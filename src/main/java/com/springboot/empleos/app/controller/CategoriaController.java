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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String formEditar(@PathVariable(value = "id")Long id, RedirectAttributes flash, Model model) {
		
		Categoria categoria = new Categoria();
		
		if(id > 0) {
			categoria = categoriaService.buscarCategoria(id);
			if(categoria == null) {
				flash.addFlashAttribute("error", "La categoria no existe");
				return "redirect:/categorias/listaCategorias";
				
			}
		}else {
			flash.addFlashAttribute("error", "El ID de la vacante no puede ser cero");
			return "redirect:/categorias/listaCategorias";
		}
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("categoria", categoria);
		
		return "categorias/formCategoria";
	}
	
	@PostMapping("/formCategoria")
	public String save(@Valid Categoria categoria, BindingResult result, SessionStatus status, RedirectAttributes flash, Model model) {
		
		String mensajeFlash = (categoria.getId() != null ? "La categoria se ha editado con éxito" : "La categoria se ha guardado con éxito");
		
		if(result.hasErrors()) {
			return "categorias/formCategoria";
		}
		
		categoriaService.save(categoria);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/categorias/listaCategorias";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id")Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			categoriaService.delete(id);
			flash.addFlashAttribute("success", "La categoria de ha eliminado con éxito");
		}
		
		return "redirect:/categorias/listaCategorias";
	}

}
