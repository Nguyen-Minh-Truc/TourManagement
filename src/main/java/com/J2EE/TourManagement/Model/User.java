package com.J2EE.TourManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")

public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

  @NotBlank(message = "Tên người dùng không được để trống.")
  private String fullName;
  @NotBlank(message = "Email người dùng không được để trống.")
  private String email;
  @NotBlank(message = "Mật khẩu người dùng không được để trống.")
  private String password;

  private boolean status;

  @Column(columnDefinition = "MEDIUMTEXT") private String refreshToken;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant updatedAt;

  @OneToMany(mappedBy = "user")
  @JsonBackReference
  private List<Booking> bookings;

  public long getId() { return this.id; }

  public void setId(long id) { this.id = id; }

  public String getFullName() { return this.fullName; }

  public void setFullName(String fullName) { this.fullName = fullName; }

  public String getEmail() { return this.email; }

  public void setEmail(String email) { this.email = email; }

  public String getPassword() { return this.password; }

  public void setPassword(String password) { this.password = password; }

  public boolean isStatus() { return this.status; }

  public boolean getStatus() { return this.status; }

  public void setStatus(boolean status) { this.status = status; }

  public String getRefreshToken() { return this.refreshToken; }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Instant getCreatedAt() { return this.createdAt; }

  public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

  public Instant getUpdatedAt() { return this.updatedAt; }

  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

  public List<Booking> getBookings() { return this.bookings; }

  public void setBookings(List<Booking> bookings) { this.bookings = bookings; }

  @PrePersist
  public void handleBeforeCreate() {
    this.createdAt = Instant.now();
  }

  @PreUpdate
  public void handleBeforeUpdate() {
    this.updatedAt = Instant.now();
  }
}
