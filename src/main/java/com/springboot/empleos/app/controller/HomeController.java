package com.springboot.empleos.app.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.empleos.app.models.entity.Vacante;
import com.springboot.empleos.app.models.service.IVacanteService;

@Controller
public class HomeController {
	
	@Autowired
	private IVacanteService vacanteService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("vacantes", vacanteService.buscarPorEstatusAndDestacado("Aprobada", 1));
		
		return "home";
	}
	
	@GetMapping("/detalle/{id}")
	public String veDetalle(@PathVariable(value = "id")Long id, Model model) {
		
		Vacante vacante = vacanteService.findOne(id);
		
		if(vacante == null) {
			return "redirect:/home";
		}
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("vacante", vacante);
		
		return "verDetalle";
	}
	
	@ModelAttribute("anio")
	public int getAnio() {
		
		Calendar cal = Calendar.getInstance();
		int anio = cal.get((Calendar.YEAR));
		
		return anio;
	}

}
