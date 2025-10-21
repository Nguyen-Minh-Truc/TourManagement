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

    private String title;

    private String shortDesc;

    private String longDesc;

    private String imageUrl;

    private Integer durationDay;

    private Integer capacity;

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
