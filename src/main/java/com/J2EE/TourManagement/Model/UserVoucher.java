package com.J2EE.TourManagement.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_vouchers")
@Getter
@Setter
public class UserVoucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("user-userVoucher")
    private User user;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    @JsonBackReference("voucher-userVoucher")
    private Voucher voucher;

    private Boolean isUsed = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant usedDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant createdAt;

    private String uniqueCode;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = Instant.now();
    }


    public double calculateDiscount(double totalAmount) {
        if (isUsed == null || isUsed) return 0.0;

        if (voucher.getDiscountType() == Voucher.DiscountType.PERCENT) {
            double discount = totalAmount * voucher.getDiscountValue() / 100;
            if (voucher.getMaxDiscount() != null) {
                discount = Math.min(discount, voucher.getMaxDiscount());
            }
            return discount;
        } else { // AMOUNT
            return Math.min(voucher.getDiscountValue(), totalAmount);
        }
    }
}
