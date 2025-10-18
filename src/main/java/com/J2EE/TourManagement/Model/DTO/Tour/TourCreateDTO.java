package com.J2EE.TourManagement.Model.DTO.Tour;

import jakarta.validation.constraints.*;

public class TourCreateDTO {

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @Size(max = 500, message = "Mô tả ngắn không được vượt quá 500 ký tự")
    private String shortDesc;

    private String longDesc;

    @NotNull(message = "Vui lòng nhập số ngày tour")
    @Positive(message = "Thời lượng tour phải lớn hơn 0")
    private Integer durationDay;

    @NotNull(message = "Vui lòng nhập sức chứa")
    @Positive(message = "Sức chứa phải lớn hơn 0")
    private Integer capacity;

    @NotBlank(message = "Điểm đến không được để trống")
    @Size(max = 255, message = "Điểm đến không được vượt quá 255 ký tự")
    private String destination;

    private String status; // tùy chọn, nếu không nhập thì entity sẽ set DRAFT

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
}
