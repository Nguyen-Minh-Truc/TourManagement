package com.J2EE.TourManagement.Model.DTO;

import com.J2EE.TourManagement.Model.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.util.List;

public class UserDTO {
  private long id;

  private String fullName;

  private String email;

  private boolean status;


  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant updatedAt;


   private List<Booking> bookings;

    public UserDTO() {
    }

    public UserDTO(long id, String fullName, String email, boolean status, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStatus() {
        return this.status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }


}
