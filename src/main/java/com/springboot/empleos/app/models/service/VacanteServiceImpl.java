package com.springboot.empleos.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.empleos.app.models.dao.IVacanteDao;
import com.springboot.empleos.app.models.entity.Vacante;

@Service
public class VacanteServiceImpl implements IVacanteService {
	
	@Autowired
	private IVacanteDao vacanteDao;

	@Override
	public List<Vacante> listaVacantes() {
		// TODO Auto-generated method stub
		return vacanteDao.findAll();
	}

	@Override
	public List<Vacante> buscarPorEstatusAndDestacado(String estatus, int destacado) {
		// TODO Auto-generated method stub
		return vacanteDao.findByEstatusAndDestacado(estatus, destacado);
	}

	@Override
	public Vacante findOne(Long id) {
		// TODO Auto-generated method stub
		return vacanteDao.findById(id).orElse(null);
	}

	@Override
	public void save(Vacante vacante) {
		// TODO Auto-generated method stub
		vacanteDao.save(vacante);
		
	}

}
