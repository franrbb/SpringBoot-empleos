package com.springboot.empleos.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.empleos.app.models.dao.ICategoriaDao;
import com.springboot.empleos.app.models.entity.Categoria;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
	
	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	public List<Categoria> listaCategorias() {
		// TODO Auto-generated method stub
		return categoriaDao.findAll();
	}

	@Override
	public void save(Categoria categoria) {
		// TODO Auto-generated method stub
		categoriaDao.save(categoria);
	}

}
