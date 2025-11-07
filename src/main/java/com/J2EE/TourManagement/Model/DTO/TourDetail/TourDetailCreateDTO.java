package com.J2EE.TourManagement.Model.DTO.TourDetail;

import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceCreateDTO;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class TourDetailCreateDTO {

    @NotNull(message = "ID tour cha không được để trống")
    private Long tourId;

    @NotBlank(message = "Điểm khởi hành không được để trống")
    private String startLocation;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDay;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @FutureOrPresent(message = "Ngày kết thúc phải là hiện tại hoặc trong tương lai")
    private LocalDate endDay;

    @Pattern(regexp = "ACTIVE|INACTIVE|DRAFT", message = "Trạng thái phải là ACTIVE, INACTIVE hoặc DRAFT")
    private String status;

    // Danh sách giá tour khi tạo mới (tùy chọn)
    private List<TourPriceCreateDTO> tourPrices;

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
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

    public List<TourPriceCreateDTO> getTourPrices() {
        return tourPrices;
    }

    public void setTourPrices(List<TourPriceCreateDTO> tourPrices) {
        this.tourPrices = tourPrices;
    }
}