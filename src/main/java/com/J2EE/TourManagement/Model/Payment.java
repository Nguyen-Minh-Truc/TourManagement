package com.J2EE.TourManagement.Model;

import com.J2EE.TourManagement.Util.constan.EnumStatusPayment;
import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

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

  private Instant createAt;

  private Instant updatedAt;

  // Getter & Setter
}
