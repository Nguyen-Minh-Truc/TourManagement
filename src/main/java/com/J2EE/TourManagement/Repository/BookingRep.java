package com.J2EE.TourManagement.Repository;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.User;


import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Repository
public interface BookingRep extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    List<Booking> findByUser(User user);
    Optional<Booking> findByUserIdAndStatus(Long userId, EnumStatusBooking status);

    @Query("SELECT b FROM Booking b WHERE b.status = 'PENDING' AND (b.expiredAt < :now OR b.expiredAt IS NULL)")
    List<Booking> findPendingAndExpired(@Param("now") Instant now);
}
