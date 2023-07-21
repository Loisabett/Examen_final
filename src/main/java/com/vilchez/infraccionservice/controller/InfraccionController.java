package com.vilchez.infraccionservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vilchez.infraccionservice.converter.InfraccionConverter;
import com.vilchez.infraccionservice.dto.InfraccionDTO;
import com.vilchez.infraccionservice.entity.Infraccion;
import com.vilchez.infraccionservice.service.InfraccionServices;

@RestController
@RequestMapping("/v1/infracciones")
public class InfraccionController {
	@Autowired
	private InfraccionServices service;
	@Autowired
	private InfraccionConverter converterInf;
	@GetMapping()
	public ResponseEntity<List<InfraccionDTO>> getAll(
			@RequestParam(value="dni",required=false,defaultValue="") String dni,
			@RequestParam(value="offset",required=false,defaultValue="0") int pageNumber,
			@RequestParam(value="limit",required=false,defaultValue="5") int pageSize
			){
		Pageable page=PageRequest.of(pageNumber,pageSize);
		List<Infraccion> infracciones;
		if(dni == null) {
			infracciones=service.findAll(page);
		}else {
			infracciones=service.findByDni(dni, page);
		}
		if(infracciones.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		List<InfraccionDTO> infraccionDTO = converterInf.fromEntity(infracciones);
		return ResponseEntity.ok(infraccionDTO);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<InfraccionDTO> getById(@PathVariable("id") int id) {
		Infraccion infraccion = service.findById(id);
		InfraccionDTO infraccionDTO=converterInf.fromEntity(infraccion);
		return ResponseEntity.status(HttpStatus.OK).body(infraccionDTO);
	}
	@GetMapping(value="/Usuario/{dni}")
	public ResponseEntity<InfraccionDTO> getByDni(@PathVariable("dni") String dni) {
		Infraccion infraccion = service.findByDni(dni);
		InfraccionDTO infraccionDTO=converterInf.fromEntity(infraccion);
		return ResponseEntity.status(HttpStatus.OK).body(infraccionDTO);
	}
	
	@PostMapping
	public ResponseEntity<Infraccion> create(@RequestBody Infraccion infraccion) {
		Infraccion infraccionDB=service.create(infraccion);
		return ResponseEntity.status(HttpStatus.CREATED).body(infraccionDB);
	}
	
	@DeleteMapping(value="/{id}")
	public int delete(@PathVariable("id") int id){
		return service.delete(id);
	}
}
