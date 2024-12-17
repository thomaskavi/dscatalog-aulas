package com.thomaskavi.dscatalog.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thomaskavi.dscatalog.controller.ProductController;
import com.thomaskavi.dscatalog.dto.ProductDTO;
import com.thomaskavi.dscatalog.services.ProductService;
import com.thomaskavi.dscatalog.services.exceptions.DatabaseException;
import com.thomaskavi.dscatalog.services.exceptions.ResourceNotFoundException;
import com.thomaskavi.dscatalog.tests.Factory;

@WebMvcTest(value = ProductController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class ProductControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @SuppressWarnings("removal")
  @MockBean
  private ProductService service;

  @Autowired
  private ObjectMapper objectMapper;

  private long existingId;
  private long nonExistingId;
  private long dependentId;
  private ProductDTO productDTO;
  private PageImpl<ProductDTO> page;

  @BeforeEach
  void setUp() throws Exception {
    productDTO = Factory.createProductDTO();
    page = new PageImpl<>(List.of(productDTO));
    existingId = 1L;
    nonExistingId = 2L;
    dependentId = 3L;

    when(service.findAllPaged("Nome produto", "Categoria", page.getPageable())).thenReturn(page);

    when(service.findById(existingId)).thenReturn(productDTO);
    when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

    when(service.update(eq(existingId), any())).thenReturn(productDTO);
    when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

    doNothing().when(service).delete(existingId);
    doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
    doThrow(DatabaseException.class).when(service).delete(dependentId);

    when(service.insert(any())).thenReturn(productDTO);
  }

  @Test
  public void findAllShouldReturnPage() throws Exception {
    ResultActions result = mockMvc.perform(get("/products")
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  public void findByIdShouldReturnProductDtoWhenIdExists() throws Exception {
    ResultActions result = mockMvc.perform(get("/products/{id}", existingId)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.description").exists());
    result.andExpect(jsonPath("$.price").exists());
  }

  @Test
  public void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
    ResultActions result = mockMvc.perform(get("/products/{id}", nonExistingId)
        .accept(MediaType.APPLICATION_JSON));
    result.andExpect(status().isNotFound());
  }

  @Test
  public void updateShouldReturnProductDtoWhenIdExists() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(productDTO);

    ResultActions result = mockMvc.perform(put("/products/{id}", existingId)
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.description").exists());
    result.andExpect(jsonPath("$.price").exists());

  }

  @Test
  public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(productDTO);

    ResultActions result = mockMvc.perform(put("/products/{id}", nonExistingId)
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void inserShouldReturnProductDtoCreated() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(productDTO);

    ResultActions result = mockMvc.perform(post("/products")
        .content(jsonBody)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(productDTO.getId()))
        .andExpect(jsonPath("$.name").value(productDTO.getName()));
  }

  @Test
  public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
    ResultActions result = mockMvc.perform(delete("/products/{id}", existingId)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNoContent());
  }

  @Test
  public void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {
    ResultActions result = mockMvc.perform(delete("/products/{id}", nonExistingId)
        .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}