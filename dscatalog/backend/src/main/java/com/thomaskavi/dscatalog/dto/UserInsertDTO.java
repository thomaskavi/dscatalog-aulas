package com.thomaskavi.dscatalog.dto;

import com.thomaskavi.dscatalog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

  private String password;

  UserInsertDTO() {
    super();
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
