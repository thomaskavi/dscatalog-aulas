package com.thomaskavi.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.thomaskavi.dscatalog.entities.Category;
import com.thomaskavi.dscatalog.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

  private Long id;

  @Size(min = 5, max = 60, message = "O nome do produto deve ter entre 5 e 60 caracteres")
  @NotBlank(message = "O nome do produto é obrigatório e não pode estar vazio.")
  private String name;

  @Size(max = 200, message = "Descrição muito longa")
  @NotBlank(message = "A descrição do produto é obrigatória e não pode estar vazia.")
  private String description;

  @Positive(message = "O preço do produto deve ser um valor positivo.")
  private Double price;

  private String imgUrl;

  @PastOrPresent(message = "A data de criação do produto não pode ser uma data futura.")
  private Instant date;

  @NotEmpty(message = "O produto deve estar associado a pelo menos uma categoria.")
  private List<CategoryDTO> categories = new ArrayList<>();

  public ProductDTO() {
  }

  public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgUrl = imgUrl;
    this.date = date;
  }

  public ProductDTO(Product entity) {
    id = entity.getId();
    name = entity.getName();
    description = entity.getDescription();
    price = entity.getPrice();
    imgUrl = entity.getImgUrl();
    date = entity.getDate();
  }

  public ProductDTO(Product entity, Set<Category> categories) {
    this(entity);
    categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public List<CategoryDTO> getCategories() {
    return categories;
  }

  public void setCategories(List<CategoryDTO> categories) {
    this.categories = categories;
  }

}
