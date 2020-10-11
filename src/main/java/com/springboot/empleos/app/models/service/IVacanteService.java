package com.springboot.empleos.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.empleos.app.models.entity.Vacante;

public interface IVacanteService {
	
	public List<Vacante> listaVacantes();
	
	public Page<Vacante> paginacion(Pageable page);
	
	public List<Vacante> buscarPorEstatusAndDestacado(String estatus, int destacado);
	
	public Vacante findOne(Long id);
	
	public void save(Vacante vacante);
	
	public void delete(Long id);

}
