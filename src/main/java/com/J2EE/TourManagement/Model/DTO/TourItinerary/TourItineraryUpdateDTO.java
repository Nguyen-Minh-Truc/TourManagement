package com.J2EE.TourManagement.Model.DTO.TourItinerary;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TourItineraryUpdateDTO {

    @NotNull(message = "ID lịch trình không được để trống")
    private Long id;

    @NotBlank(message = "Tiêu đề lịch trình không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @NotBlank(message = "Nội dung lịch trình không được để trống")
    private String content;
}