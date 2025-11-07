package com.J2EE.TourManagement.Model.DTO.TourItinerary;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TourItineraryResponseDTO {

    private Long id;
    private Long tourDetailId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}