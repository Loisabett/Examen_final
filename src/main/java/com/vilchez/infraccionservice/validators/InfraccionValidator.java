package com.vilchez.infraccionservice.validators;

import com.vilchez.infraccionservice.entity.Infraccion;
import com.vilchez.infraccionservice.exception.ValidateServiceException;

public class InfraccionValidator {
	public static void save(Infraccion infraccion) {
		if(infraccion.getDni()==null || infraccion.getDni().trim().isEmpty()) {
			throw new ValidateServiceException("El DNI es requerido");
		}
		if(infraccion.getDni().length()>8) {
			throw new ValidateServiceException("El DNI debe contener solo 8 caracteres");
		}
		if(infraccion.getFalta().length()>3) {
			throw new ValidateServiceException("Las Faltas no debe exceder 3 caracteres");
		}
	}
}
