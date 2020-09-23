package com.springboot.empleos.app.models.service;

import java.util.List;

import com.springboot.empleos.app.models.entity.Vacante;

public interface IVacanteService {
	
	public List<Vacante> listaVacantes();
	
	public List<Vacante> buscarPorEstatusAndDestacado(String estatus, int destacado);
	
	public Vacante findOne(Long id);
	
	public void save(Vacante vacante);

}
