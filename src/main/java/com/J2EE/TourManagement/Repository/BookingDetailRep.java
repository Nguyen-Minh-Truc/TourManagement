package com.J2EE.TourManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.J2EE.TourManagement.Model.BookingDetail;

@Repository
public interface BookingDetailRep extends JpaRepository<BookingDetail, Long> {
    
}
