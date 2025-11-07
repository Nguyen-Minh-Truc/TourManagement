package com.J2EE.TourManagement.Model.DTO.TourDetail;

import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceUpdateDTO;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class TourDetailUpdateDTO {

    @NotNull(message = "ID không được để trống khi cập nhật")
    private Long id;

    private String startLocation;

    private LocalDate startDay;

    @FutureOrPresent(message = "Ngày kết thúc phải là hiện tại hoặc trong tương lai")
    private LocalDate endDay;

    @Pattern(regexp = "ACTIVE|INACTIVE|DRAFT", message = "Trạng thái phải là ACTIVE, INACTIVE hoặc DRAFT")
    private String status;

    // Danh sách giá (nếu cần cập nhật cùng lúc)
    private List<TourPriceUpdateDTO> tourPrices;

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

    public List<TourPriceUpdateDTO> getTourPrices() {
        return tourPrices;
    }

    public void setTourPrices(List<TourPriceUpdateDTO> tourPrices) {
        this.tourPrices = tourPrices;
    }
}