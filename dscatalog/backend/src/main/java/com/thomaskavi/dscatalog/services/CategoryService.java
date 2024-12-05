package com.thomaskavi.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thomaskavi.dscatalog.entities.Category;
import com.thomaskavi.dscatalog.repository.CategoryRepository;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository repository;

  public List<Category> findAll() {
    return repository.findAll();
  }
}
