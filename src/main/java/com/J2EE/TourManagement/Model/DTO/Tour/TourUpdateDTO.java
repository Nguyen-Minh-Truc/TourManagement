package com.J2EE.TourManagement.Model.DTO.Tour;

import jakarta.validation.constraints.*;
import jdk.jfr.SettingDefinition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TourUpdateDTO {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @Size(max = 500, message = "Mô tả ngắn không được vượt quá 500 ký tự")
    private String shortDesc;

    private String longDesc;

    private String imageUrl;

    @NotNull(message = "Vui lòng nhập số ngày tour")
    @Positive(message = "Thời lượng tour phải lớn hơn 0")
    private Integer durationDay;

    @NotNull(message = "Vui lòng nhập sức chứa")
    @Positive(message = "Sức chứa phải lớn hơn 0")
    private Integer capacity;

    @NotBlank(message = "Điểm đến không được để trống")
    @Size(max = 255, message = "Điểm đến không được vượt quá 255 ký tự")
    private String destination;

    @Pattern(regexp = "ACTIVE|INACTIVE|DRAFT", message = "Trạng thái phải là ACTIVE, INACTIVE hoặc DRAFT")
    private String status;
}
