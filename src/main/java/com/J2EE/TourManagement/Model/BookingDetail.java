package com.J2EE.TourManagement.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "bookingdetail")
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_booking", nullable = false)
    @JsonBackReference
    private Booking booking;

    @Column(name = "id_tourDetail")
    private long tourDetailId;

    @Column(name = "id_tourPrice")
    private long tourPriceId;

    private boolean status;

    private int quantity;

    private double price;

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Long getTourDetailId() {
        return tourDetailId;
    }

    public void setTourDetailId(Long tourDetailId) {
        this.tourDetailId = tourDetailId;
    }

    public Long getTourPriceId() {
        return tourPriceId;
    }

    public void setTourPriceId(Long tourPriceId) {
        this.tourPriceId = tourPriceId;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
