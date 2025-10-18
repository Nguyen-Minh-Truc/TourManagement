package com.J2EE.TourManagement.Model.DTO.Tour;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class TourPriceCreateDTO {

    @NotBlank(message = "Loại giá không được để trống")
    @Pattern(regexp = "ADULT|CHILD|INFANT|GROUP", message = "Loại giá phải là ADULT, CHILD, INFANT hoặc GROUP")
    private String priceType;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
