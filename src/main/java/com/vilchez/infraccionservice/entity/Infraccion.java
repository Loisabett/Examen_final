package com.vilchez.infraccionservice.entity;

import java.sql.Date;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="infracciones")
@EntityListeners(AuditingEntityListener.class)
public class Infraccion {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true,nullable = false, length = 8)
	private String dni;	
	
	@Column(name="fecha",nullable=false, columnDefinition="date", unique=false)
	private Date fecha;
	
	@Column(name="falta",nullable=false, length = 3)
	private String falta;
	
	@Column(name="infraccion",nullable=false, length = 200)
	private String infraccion;
	
	@Column(name="descripcion",nullable=true, length = 255)
	private String descripcion;
	

	@Column(name="estado",nullable=false, columnDefinition="bit default 1")
	private int estado;
	
	@Embedded
	private TimeStamp timeStamp;
}
