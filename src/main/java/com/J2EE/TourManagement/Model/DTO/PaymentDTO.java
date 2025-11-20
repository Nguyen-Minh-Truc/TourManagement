package com.J2EE.TourManagement.Model.DTO;

import jakarta.validation.constraints.NotBlank;

public class PaymentDTO {
    
    private long id_booking;

    private Long userVoucherId; // optional, có thể null nếu không dùng voucher
   
    public long getId_booking() {
        return this.id_booking;
    }

    public void setId_booking(long id_booking) {
        this.id_booking = id_booking;
    }

    public Long getUserVoucherId() { return userVoucherId; }

    public void setUserVoucherId(Long userVoucherId) { this.userVoucherId = userVoucherId; }
}
