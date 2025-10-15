package com.J2EE.TourManagement.Model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Tour {
    private Long id;
    private String title;
    private String shortDesc;
    private String longDesc;
    private Integer durationDay;
    private Integer capacity;
    private String destination;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    // Quan hệ (nếu dùng JPA)
    private List<TourDetail> tourDetails;
}
