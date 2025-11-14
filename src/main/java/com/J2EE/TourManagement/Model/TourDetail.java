package com.J2EE.TourManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "tour_details")
@Getter
@Setter
public class TourDetail {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tour_id")
  @JsonBackReference
  private Tour tour;

  @Column(name = "tour_id", insertable = false, updatable = false)
  private Long tourId;

  @Column(name = "startLocation")
  @NotBlank(message = "Điểm khởi hành không được để trống")
  private String startLocation;

  @Column(name = "startDay")
  @NotNull(message = "Ngày bắt đầu không được để trống")
  private LocalDate startDay;

  @Column(name = "endDay")
  @NotNull(message = "Ngày kết thúc không được để trống")
  @FutureOrPresent(message =
                       "Ngày kết thúc phải là hiện tại hoặc trong tương lai")
  private LocalDate endDay;

  @NotBlank(message = "Trạng thái không được để trống")
  @Pattern(regexp = "ACTIVE|INACTIVE|DRAFT",
           message = "Trạng thái phải là ACTIVE, INACTIVE hoặc DRAFT")

  private String status;

  @Column(updatable = false, name = "createdAt")
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "tourDetail", cascade = CascadeType.ALL,
             orphanRemoval = true, fetch = FetchType.EAGER)
  @JsonManagedReference(value = "detail-price")
  private List<TourPrice> tourPrices;

  // Quan hệ 1 TourDetail có 1 Itinerary
  @OneToOne(mappedBy = "tourDetail", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.LAZY)
  @JsonManagedReference(value = "detail-itinerary")
  private TourItinerary itinerary;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.status = (this.status == null) ? "DRAFT" : this.status;
  }
}
