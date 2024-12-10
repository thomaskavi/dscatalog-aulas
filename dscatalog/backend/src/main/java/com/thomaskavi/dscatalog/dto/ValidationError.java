package com.thomaskavi.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

  private List<FieldMessageDTO> errors = new ArrayList<>();

  public ValidationError(Instant timestamp, Integer status, String message, String error, String path) {
    super(timestamp, status, message, error, path);
  }

  public ValidationError(Instant timestamp, Integer status, String message, String path) {
    super(timestamp, status, message, path);
  }

  public List<FieldMessageDTO> getErrors() {
    return errors;
  }

  public void addError(String fieldName, String message) {

    errors.removeIf(x -> x.getFieldName().equals(fieldName));

    errors.add(new FieldMessageDTO(fieldName, message));
  }
}