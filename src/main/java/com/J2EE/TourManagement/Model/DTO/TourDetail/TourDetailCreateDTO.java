package com.J2EE.TourManagement.Model.DTO.TourDetail;

import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceCreateDTO;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import java.time.LocalDate;
import java.util.List;

@Data
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
}