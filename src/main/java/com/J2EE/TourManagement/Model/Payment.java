package com.J2EE.TourManagement.Model;

import com.J2EE.TourManagement.Util.constan.EnumStatusPayment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "payment")
public class Payment {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  // Khóa ngoại tới Booking
  @ManyToOne
  @JoinColumn(name = "id_booking", nullable = false)
  private Booking booking;

  @ManyToOne @JoinColumn(name = "provider_id") private PaymentProvider provider;

  private Double amount;
  private String method;

  @Enumerated(EnumType.STRING) private EnumStatusPayment status;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:s a", timezone = "GMT+7")
  private Instant updatedAt;

  public Long getId() { return this.id; }

  public void setId(Long id) { this.id = id; }

  public Booking getBooking() { return this.booking; }

  public void setBooking(Booking booking) { this.booking = booking; }

  public PaymentProvider getProvider() { return this.provider; }

  public void setProvider(PaymentProvider provider) {
    this.provider = provider;
  }

  public Double getAmount() { return this.amount; }

  public void setAmount(Double amount) { this.amount = amount; }

  public String getMethod() { return this.method; }

  public void setMethod(String method) { this.method = method; }

  public EnumStatusPayment getStatus() { return this.status; }

  public void setStatus(EnumStatusPayment status) { this.status = status; }

  public Instant getCreateAt() { return this.createdAt; }

  public void setCreateAt(Instant createAt) { this.createdAt = createAt; }

  public Instant getUpdatedAt() { return this.updatedAt; }

  public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

  @PrePersist
  public void handleBeforeCreate() {
    this.createdAt = Instant.now();
  }

  @PreUpdate
  public void handleBeforeUpdate() {
    this.updatedAt = Instant.now();
  }
}
