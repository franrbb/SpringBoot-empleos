package com.springboot.empleos.app.models.service;

import java.util.List;


import com.springboot.empleos.app.models.entity.Usuario;

public interface IUsuarioService {
	
	public List<Usuario> listaUsuarios();
	
	public void save(Usuario usuario);

}
