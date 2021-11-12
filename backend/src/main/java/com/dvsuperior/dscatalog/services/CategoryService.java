package com.dvsuperior.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dvsuperior.dscatalog.entities.Category;
import com.dvsuperior.dscatalog.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	CategoryRepository categoryRepository;
	
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
}
