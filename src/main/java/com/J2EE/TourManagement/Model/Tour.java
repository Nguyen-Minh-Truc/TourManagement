package com.J2EE.TourManagement.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Getter
@Setter
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    private String img;

    @Size(max = 500, message = "Mô tả ngắn không được vượt quá 500 ký tự")
    private String shortDesc;

    private String longDesc;

    @NotNull(message = "Vui lòng nhập số ngày tour")
    @Positive(message = "Thời lượng tour phải lớn hơn 0")
    private Integer durationDay;

    @NotNull(message = "Vui lòng nhập sức chứa")
    @Positive(message = "Sức chứa phải lớn hơn 0")
    private Integer capacity;

    @NotBlank(message = "Điểm đến không được để trống")
    @Size(max = 255, message = "Điểm đến không được vượt quá 255 ký tự")
    private String destination;

    @NotBlank(message = "Trạng thái không được để trống")
    @Pattern(regexp = "ACTIVE|INACTIVE|DRAFT", message = "Trạng thái phải là ACTIVE, INACTIVE hoặc DRAFT")
    private String status;


    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TourDetail> tourDetails;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = (this.status == null) ? "DRAFT" : this.status;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
