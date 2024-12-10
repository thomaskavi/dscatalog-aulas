package com.thomaskavi.dscatalog.dto;

import java.util.HashSet;
import java.util.Set;

import com.thomaskavi.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class UserDTO {

  private Long id;

  @NotBlank(message = "O primeiro nome é obrigatório e não pode estar vazio.")
  private String firstName;

  private String lastName;

  @NotBlank(message = "O e-mail é obrigatório e não pode estar vazio.")
  @Email(message = "Informe um e-mail válido no formato: exemplo@dominio.com.")
  private String email;

  @NotEmpty(message = "O usuário deve ter pelo menos uma função associada.")
  private Set<RoleDTO> roles = new HashSet<>();

  public UserDTO() {
  }

  public UserDTO(Long id, String firstName, String lastName, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public UserDTO(User entity) {
    id = entity.getId();
    firstName = entity.getFirstName();
    lastName = entity.getLastName();
    email = entity.getEmail();
    entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<RoleDTO> getRoles() {
    return roles;
  }

}
