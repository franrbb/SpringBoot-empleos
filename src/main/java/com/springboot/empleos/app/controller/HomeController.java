package com.springboot.empleos.app.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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
	
	@ModelAttribute("anio")
	public int getAnio() {
		
		Calendar cal = Calendar.getInstance();
		int anio = cal.get((Calendar.YEAR));
		
		return anio;
	}

}
