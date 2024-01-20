package saf.io.passwordmanager.model;

import lombok.Data;

@Data
public class CredentialsRequest {
  private String accountName;

  private String userName;

  private String password;
}
