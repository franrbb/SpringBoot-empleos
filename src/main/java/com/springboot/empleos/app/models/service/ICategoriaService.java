package com.springboot.empleos.app.models.service;

import java.util.List;

import com.springboot.empleos.app.models.entity.Categoria;

public interface ICategoriaService {
	
	public List<Categoria> listaCategorias();
	
	public void save(Categoria categoria);
	
	public Categoria buscarCategoria(Long id);
	
	public void delete(Long id);

}
