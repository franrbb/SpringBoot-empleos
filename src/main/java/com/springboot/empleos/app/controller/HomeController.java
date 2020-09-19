package com.springboot.empleos.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springboot.empleos.app.models.service.IVacanteService;

@Controller
public class HomeController {
	
	@Autowired
	private IVacanteService vacanteService;
	
	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("titulo", "Lista de Vacantes disponibles");
		model.addAttribute("vacantes", vacanteService.listaVacantes());
		
		return "home";
	}

}
