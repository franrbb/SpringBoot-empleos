package com.springboot.empleos.app.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.empleos.app.models.entity.Vacante;
import com.springboot.empleos.app.models.service.ICategoriaService;
import com.springboot.empleos.app.models.service.IUploadsService;
import com.springboot.empleos.app.models.service.IVacanteService;

@Controller
@RequestMapping("/vacantes")
@SessionAttributes("vacante")
public class VacanteController {
	
	@Autowired
	private IVacanteService vacanteService;
	
	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private IUploadsService uploadsService;
	
	@GetMapping("/listaVacantes")
	public String listado(Model model){
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		model.addAttribute("vacantes", vacanteService.listaVacantes());
		
		return "vacantes/listaVacantes";
	}
	
	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPaginado(Model model, Pageable page) {
		
		model.addAttribute("titulo", "EmpleosApp | Aplicación para Publicar Ofertas de Trabajo.");
		Page<Vacante> lista = vacanteService.paginacion(page);
		model.addAttribute("vacantes", lista);
		
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
	
	@GetMapping("/formVacante/{id}")
	public String editar(@PathVariable(value = "id") Long id, Vacante vacante, RedirectAttributes flash, Model model) {
		
		if(id > 0) {
			vacante = vacanteService.findOne(id);
			if(vacante == null) {
				flash.addFlashAttribute("error", "La vacante no existe");
				return "redirect:/vacantes/listaVacantes";
			}
		}else {
			flash.addFlashAttribute("error", "El ID de la vacante no puede ser cero");
			return "redirect:/vacantes/listaVacantes";
		}
		
		model.addAttribute("vacante", vacante);
		model.addAttribute("categorias", categoriaService.listaCategorias());
		
		return "vacantes/formVacante";
	}
	
	@PostMapping("/formVacante")
	public String save(@Valid Vacante vacante, BindingResult result, SessionStatus session, RedirectAttributes flash, @RequestParam(value = "archivoImagen") MultipartFile file ,Model model) {
		
		String mensajeFlash = (vacante.getId() != null) ? "Vacante editada con éxito" : "Vacante creada con éxito";
		
		if(result.hasErrors()) {
			model.addAttribute("categorias", categoriaService.listaCategorias());
			return "vacantes/formVacante";
		}
		
		if(!file.isEmpty()) {
			
			if(vacante.getId() != null && vacante.getId() > 0 && vacante.getImagen() != null && vacante.getImagen().length() > 0) {
				uploadsService.delete(vacante.getImagen());
			}
			
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadsService.copy(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			vacante.setImagen(uniqueFilename);
		}
			
		
		vacanteService.save(vacante);
		session.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/vacantes/listaVacantes";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if(id > 0) {
			Vacante vacante = vacanteService.findOne(id);
			vacanteService.delete(id);
			flash.addFlashAttribute("success", "La vacante se ha eliminado con éxito");
			
			uploadsService.delete(vacante.getImagen());
			
		}
		
		return "redirect:/vacantes/listaVacantes";
	}

}
