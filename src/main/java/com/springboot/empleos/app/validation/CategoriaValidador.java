package com.springboot.empleos.app.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springboot.empleos.app.models.entity.Categoria;

@Component
public class CategoriaValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Categoria.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Categoria categoria = (Categoria) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.categoria.nombre");
		
		if(categoria.getDescripcion().isEmpty()) {
			errors.rejectValue("descripcion", "NotEmpty.categoria.descripcion");
		}

	}

}
