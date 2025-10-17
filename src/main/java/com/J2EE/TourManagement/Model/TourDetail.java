package com.J2EE.TourManagement.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "tourDetail")
public class TourDetail {
    @Id
    private Long id;

    private Long idTour;

    private String startLocation;
    private Instant startDay;
    private Instant endDay;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

}
