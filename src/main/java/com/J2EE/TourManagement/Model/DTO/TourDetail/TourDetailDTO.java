package com.J2EE.TourManagement.Model.DTO.TourDetail;

import com.J2EE.TourManagement.Model.DTO.Review.ReviewResponseDTO;
import com.J2EE.TourManagement.Model.DTO.TourItinerary.TourItineraryResponseDTO;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceDTO;
import com.J2EE.TourManagement.Model.TourItinerary;
import com.J2EE.TourManagement.Repository.ReviewRepository;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TourDetailDTO {
    private Long id;
    private String startLocation;
    private LocalDate startDay;
    private LocalDate endDay;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    private List<TourPriceDTO> tourPrices;
    private List<ReviewResponseDTO> reviews;
    private TourItineraryResponseDTO tourItinerary;

    public TourDetailDTO(Long id, String startLocation, LocalDate startDay, LocalDate endDay, String status, LocalDateTime createdAt, LocalDateTime updateAt, List<TourPriceDTO> tourPrices) {
        this.id = id;
        this.startLocation = startLocation;
        this.startDay = startDay;
        this.endDay = endDay;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.tourPrices = tourPrices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public LocalDate getStartDay() {
        return startDay;
    }

    public void setStartDay(LocalDate startDay) {
        this.startDay = startDay;
    }

    public LocalDate getEndDay() {
        return endDay;
    }

    public void setEndDay(LocalDate endDay) {
        this.endDay = endDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public List<TourPriceDTO> getTourPrices() {
        return tourPrices;
    }

    public void setTourPrices(List<TourPriceDTO> tourPrices) {
        this.tourPrices = tourPrices;
    }
}
