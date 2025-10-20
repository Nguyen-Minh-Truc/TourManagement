package com.J2EE.TourManagement.Model.DTO.Tour;

import java.time.LocalDateTime;
import java.util.List;

public class TourDTO {
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

    private List<TourDetailDTO> tourDetails;

    public TourDTO(Long id, String title, String shortDesc, String longDesc, Integer durationDay, Integer capacity, String destination, String status, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, List<TourDetailDTO> tourDetails) {
        this.id = id;
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.durationDay = durationDay;
        this.capacity = capacity;
        this.destination = destination;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.tourDetails = tourDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public Integer getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(Integer durationDay) {
        this.durationDay = durationDay;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<TourDetailDTO> getTourDetails() {
        return tourDetails;
    }

    public void setTourDetails(List<TourDetailDTO> tourDetails) {
        this.tourDetails = tourDetails;
    }
}
