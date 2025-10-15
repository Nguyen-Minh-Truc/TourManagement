package com.J2EE.TourManagement.Model;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TourDetail {
    private Long id;                 // BIGINT -> Long
    private Long idTour;             // FK -> Long
    private String startLocation;    // VARCHAR -> String
    private LocalDate startDay;      // DATE -> LocalDate
    private LocalDate endDay;        // DATE -> LocalDate
    private String status;           // ENUM -> String
    private LocalDateTime createdAt; // DATETIME -> LocalDateTime
    private LocalDateTime updateAt;  // DATETIME -> LocalDateTime

    // Quan hệ
    private Tour tour;
    private List<TourPrice> tourPrices;
}
