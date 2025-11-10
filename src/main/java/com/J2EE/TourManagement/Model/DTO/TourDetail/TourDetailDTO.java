package com.J2EE.TourManagement.Model.DTO.TourDetail;

import com.J2EE.TourManagement.Model.DTO.Review.ReviewDTO;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceDTO;
import lombok.Data;
import com.J2EE.TourManagement.Model.DTO.TourItinerary.TourItineraryDTO;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TourDetailDTO {

    private Long id;

    private String startLocation;

    private LocalDate startDay;

    private LocalDate endDay;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    // Danh sách giá (TourPrice)
    private List<TourPriceDTO> tourPrices;

    // Danh sách review
    private List<ReviewDTO> reviews;

    // Itinerary
    private TourItineraryDTO itinerary;
}