package com.springboot.empleos.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.empleos.app.models.entity.Usuario;

public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

}
