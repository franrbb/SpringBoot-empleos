package com.springboot.empleos.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.empleos.app.models.entity.Vacante;

public interface IVacanteDao extends JpaRepository<Vacante, Long> {
	
	public List<Vacante> findByEstatusAndDestacado(String estatus, int destacado);

}
