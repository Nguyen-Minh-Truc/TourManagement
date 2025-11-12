package com.J2EE.TourManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.User;
import java.util.List;


@Repository
public interface BookingRep extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    List<Booking> findByUser(User user); 
}
