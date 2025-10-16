package com.J2EE.TourManagement.Model.DTO;

import java.util.List;

import com.J2EE.TourManagement.Model.BookingDetail;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class BookingDTO {
    
    private long userId;
    private String note;
    @Enumerated(EnumType.STRING)
    private EnumStatusBooking status;

    @NotBlank(message =  "Email người dùng không được để trống.")
    private String contactEmail;

    @NotBlank(message =  "Số điện thoại người dùng không được để trống.")
    private String contactPhone;

    private long paymentId; 

    private List<BookingDetail> bookingDetails;


    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EnumStatusBooking getStatus() {
        return this.status;
    }

    public void setStatus(EnumStatusBooking status) {
        this.status = status;
    }

    public String getContactEmail() {
        return this.contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return this.contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public long getPaymentId() {
        return this.paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public List<BookingDetail> getBookingDetails() {
        return this.bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

}

