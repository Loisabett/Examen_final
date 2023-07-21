package com.vilchez.infraccionservice.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfraccionDTO {
	private int id;
	private String dni;
	private Date fecha;
	private String falta;
	private String infracccion;
	private String descripcion;
}
