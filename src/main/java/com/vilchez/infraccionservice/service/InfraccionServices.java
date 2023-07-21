package com.vilchez.infraccionservice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.vilchez.infraccionservice.entity.Infraccion;

public interface InfraccionServices {
	public List<Infraccion> findAll(Pageable page);
	public Infraccion findById(int id);
	public Infraccion findByDni(String dni);
	public List<Infraccion> findByDni(String dni, Pageable page);
    public Infraccion create(Infraccion obj);
    public Infraccion update(Infraccion obj);
	public int delete(int id);
}
