package com.springboot.empleos.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.empleos.app.models.entity.Vacante;
import com.springboot.empleos.app.models.service.ICategoriaService;
import com.springboot.empleos.app.models.service.IVacanteService;

@Controller
@RequestMapping("/vacantes")
public class VacanteController {
	
	@Autowired
	private IVacanteService vacanteService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@GetMapping("/listaVacantes")
	public String listado(Model model){
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("vacantes", vacanteService.listaVacantes());
		
		return "vacantes/listaVacantes";
	}
	
	@GetMapping("/formVacante")
	public String form(Model model){
		
		Vacante vacante = new Vacante();
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("vacante", vacante);
		model.addAttribute("categorias", categoriaService.listaCategorias());
		
		return "vacantes/formVacante";
	}
	
	@PostMapping("/formVacante")
	public String save(Vacante vacante, Model model) {
		
		vacanteService.save(vacante);
		
		return "redirect:/vacantes/listaVacantes";
	}

}
