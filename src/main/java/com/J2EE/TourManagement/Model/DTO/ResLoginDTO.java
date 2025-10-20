package com.J2EE.TourManagement.Model.DTO;


public class ResLoginDTO {
  private String accessToken;
  private UserLogin userLogin;

  public static class UserLogin {
    private long id;
    private String email;
    private String fullname;

    public UserLogin(long id, String email, String fullname) {
      this.id = id;
      this.email = email;
      this.fullname = fullname;
    }

    public UserLogin() {}

    public long getId() { return this.id; }

    public void setId(long id) { this.id = id; }

    public String getfullname() { return this.fullname; }

    public void setfullname(String fullname) { this.fullname = fullname; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }
  }

  public String getAccessToken() { return this.accessToken; }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public UserLogin getUserLogin() { return this.userLogin; }

  public void setUserLogin(UserLogin userLogin) { this.userLogin = userLogin; }
}
