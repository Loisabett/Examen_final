package com.vilchez.infraccionservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vilchez.infraccionservice.entity.Infraccion;
import com.vilchez.infraccionservice.exception.GeneralServiceException;
import com.vilchez.infraccionservice.exception.NoDataServiceException;
import com.vilchez.infraccionservice.exception.ValidateServiceException;
import com.vilchez.infraccionservice.repository.InfraccionRepository;
import com.vilchez.infraccionservice.service.InfraccionServices;
import com.vilchez.infraccionservice.validators.InfraccionValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InfraccionServiceImpl implements InfraccionServices{
	
	@Autowired
	private InfraccionRepository repository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findAll(Pageable page) {
		try {
			return repository.findAll();
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		}catch(Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Infraccion findById(int id) {
		try {
			return repository.findById(id).orElseThrow(()->new NoDataServiceException("No existe un registro con el ID "+id));
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}
	@Override
	@Transactional(readOnly = true)
	public Infraccion findByDni(String dni) {
		try {
			return repository.findByDni(dni);
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}
	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findByDni(String dni, Pageable page) {
		try {
			return repository.findByDniContaining(dni,page);
			} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Infraccion create(Infraccion obj) {
		try {
			InfraccionValidator.save(obj);
			if(repository.findByDni(obj.getDni()) !=null) {
				throw new ValidateServiceException("Ya hay un registro con ese dni"+obj.getDni());
			}
			obj.setEstado(1);
			return repository.save(obj);			
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}		
	}

	@Override
	@Transactional
	public Infraccion update(Infraccion obj) {
		try {
			InfraccionValidator.save(obj);
			Infraccion infraccionDB=repository.findByDni(obj.getDni());
			if(infraccionDB!=null&& obj.getId()!=infraccionDB.getId()) {
				throw new ValidateServiceException("No hay un registro con ese DNI"+obj.getDni());
			}
			Infraccion infraccion=repository.findById(obj.getId()).orElseThrow(()->new NoDataServiceException("No existe un registro con el ID"+obj.getId()));
			infraccion.setDni(obj.getDni());
			infraccion.setFecha(obj.getFecha());
			infraccion.setFalta(obj.getFalta());
			infraccion.setInfraccion(obj.getInfraccion());
			infraccion.setDescripcion(obj.getDescripcion());

			return repository.save(infraccion);
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}
	@Override
	@Transactional
	public int delete(int id) {
		try {
			Infraccion infraccionDB= findById(id);
			if(infraccionDB==null) {
				return 0;
			}else {
				infraccionDB.setEstado(0);
				repository.save(infraccionDB);
				return 1;
			}
		} catch (ValidateServiceException | NoDataServiceException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
