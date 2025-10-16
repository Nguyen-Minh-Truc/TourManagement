package com.J2EE.TourManagement.Model.DTO;

import java.time.Instant;
import java.util.List;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.BookingDetail;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;

public class BookingResponseDTO {
    private long id;
    private long userId;
    private double totalPrice;
    private String note;
    private EnumStatusBooking status;
    private String contactEmail;
    private String contactPhone;
    private Instant createdAt;
    private Instant updatedAt;
    private List<BookingDetail> bookingDetails;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.userId = booking.getUser().getId();
        this.totalPrice = booking.getTotalPrice();
        this.note = booking.getNote();
        this.status = booking.getStatus();
        this.contactEmail = booking.getContactEmail();
        this.contactPhone = booking.getContactPhone();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();
        this.bookingDetails = booking.getBookingDetails();
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<BookingDetail> getBookingDetails() {
        return this.bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
