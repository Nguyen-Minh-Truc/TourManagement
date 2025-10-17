package com.J2EE.TourManagement.Repository;


import com.J2EE.TourManagement.Model.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    Page<Tour> findByStatusContainingIgnoreCaseAndDestinationContainingIgnoreCase(
            String status, String destination, Pageable pageable
    );
}
