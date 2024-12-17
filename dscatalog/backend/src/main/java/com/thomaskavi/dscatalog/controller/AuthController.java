package com.thomaskavi.dscatalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thomaskavi.dscatalog.dto.EmailDTO;
import com.thomaskavi.dscatalog.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

  @Autowired
  private AuthService service;

  @PostMapping(value = "/recover-token")
  public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body) {
    service.createRecoverToken(body);
    return ResponseEntity.noContent().build();
  }

}
