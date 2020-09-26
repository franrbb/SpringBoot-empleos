package com.springboot.empleos.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	public String save(@Valid Vacante vacante, BindingResult result ,@RequestParam(value = "archivoImagen") MultipartFile file ,Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("categorias", categoriaService.listaCategorias());
			return "vacantes/formVacante";
		}
		
		if(!file.isEmpty()) {
			
			String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			//Crear ruta donde se guardan las imagenes. Se asocia el nombre a la ruta
			Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
			//Agregamos la ruta absoluta
			Path rootAbsolutePath = rootPath.toAbsolutePath();
			
			try {
				Files.copy(file.getInputStream(), rootAbsolutePath);
				vacante.setImagen(uniqueFilename);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		vacanteService.save(vacante);
		
		return "redirect:/vacantes/listaVacantes";
	}

}
