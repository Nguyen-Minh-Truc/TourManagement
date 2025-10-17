package com.J2EE.TourManagement.Model.DTO;

import jakarta.validation.constraints.NotBlank;

public class PaymentDTO {
    
    private long id_booking;
    @NotBlank(message = "vui lòng chọn phương thức thanh toán.")
    private String method;

    public long getId_booking() {
        return this.id_booking;
    }

    public void setId_booking(long id_booking) {
        this.id_booking = id_booking;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
