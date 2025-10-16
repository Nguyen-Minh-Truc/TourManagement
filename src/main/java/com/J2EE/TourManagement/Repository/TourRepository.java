package com.J2EE.TourManagement.Repository;


import com.J2EE.TourManagement.Model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
