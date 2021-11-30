package com.dvsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dvsuperior.dscatalog.dto.CategoryDTO;
import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.repository.CategoryRepository;
import com.dvsuperior.dscatalog.services.exceptions.DatabaseException;
import com.dvsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository repository;
	
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		List<Category> list = repository.findAll();	
		return list.stream().map(x -> new  CategoryDTO(x)).collect(Collectors.toList());
	}
	
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id){
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new CategoryDTO(entity);
	
	}

	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();	
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	
	}

	
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());		
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		
		} 
		
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id "+ id +" not found.");
		}
			
	}


	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id "+ id +" not found.");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
		
	}
}