package com.J2EE.TourManagement.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "vouchers")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private String description;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private Double discountValue;
    private Double minOrder;
    private Double maxDiscount;
    private Integer quantity;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate; // ngày bắt đầu

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate; // ngày kết thúc

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    @OneToMany(mappedBy = "voucher")
    @JsonManagedReference("voucher-userVoucher")
    private List<UserVoucher> userVouchers;

    public enum DiscountType {PERCENT, AMOUNT}

    public enum VoucherStatus {ACTIVE, INACTIVE}

    @PrePersist
    public void handleBeforeCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
