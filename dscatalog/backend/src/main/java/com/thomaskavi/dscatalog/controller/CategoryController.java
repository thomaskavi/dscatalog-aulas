package com.thomaskavi.dscatalog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomaskavi.dscatalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

  @GetMapping
  public ResponseEntity<List<Category>> findAll() {
    List<Category> list = new ArrayList<>();
    list.add(new Category(1L, "Livros"));
    list.add(new Category(2L, "Eletronics"));
    return ResponseEntity.ok().body(list);
  }

}
