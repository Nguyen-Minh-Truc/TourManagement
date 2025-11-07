package com.J2EE.TourManagement.Model.DTO.Review;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {

    private Long id;
    private Long tourDetailId;
    private String reviewerName;
    private String content;
    private Integer rating;
    private String imageUrl;
    private LocalDateTime createdAt;
}