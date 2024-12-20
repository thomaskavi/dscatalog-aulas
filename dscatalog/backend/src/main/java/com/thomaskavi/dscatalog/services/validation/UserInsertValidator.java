package com.thomaskavi.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thomaskavi.dscatalog.controller.handlers.FieldMessage;
import com.thomaskavi.dscatalog.dto.UserInsertDTO;
import com.thomaskavi.dscatalog.entities.User;
import com.thomaskavi.dscatalog.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

  @Autowired
  private UserRepository repository;

  @Override
  public void initialize(UserInsertValid ann) {
  }

  @Override
  public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

    List<FieldMessage> list = new ArrayList<>();
    User user = repository.findByEmail(dto.getEmail());

    if (user != null) {
      list.add(new FieldMessage("email", "Email já está sendo utilizado por outro usuário"));
    }

    for (FieldMessage e : list) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
          .addConstraintViolation();
    }
    return list.isEmpty();
  }
}