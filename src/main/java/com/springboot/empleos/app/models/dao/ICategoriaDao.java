package com.springboot.empleos.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.empleos.app.models.entity.Categoria;

public interface ICategoriaDao extends JpaRepository<Categoria, Long>{

}
