package com.thomaskavi.dscatalog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thomaskavi.dscatalog.dto.CategoryDTO;
import com.thomaskavi.dscatalog.entities.Category;
import com.thomaskavi.dscatalog.repository.CategoryRepository;
import com.thomaskavi.dscatalog.services.exceptions.DatabaseException;
import com.thomaskavi.dscatalog.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true)
  public Page<CategoryDTO> findAllPaged(Pageable pageable) {
    Page<Category> list = repository.findAll(pageable);
    return list.map(x -> new CategoryDTO(x));
  }

  @Transactional(readOnly = true)
  public CategoryDTO findById(Long id) {
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
      Category entity = repository.getReferenceById(id);
      entity.setName(dto.getName());
      entity = repository.save(entity);
      return new CategoryDTO(entity);
    } catch (EntityNotFoundException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new ResourceNotFoundException("Resource not found " + id);
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity referential violation");
    }
  }

}
