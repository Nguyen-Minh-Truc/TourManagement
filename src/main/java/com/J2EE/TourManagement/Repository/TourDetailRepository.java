package com.J2EE.TourManagement.Repository;

import com.J2EE.TourManagement.Model.TourDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourDetailRepository extends JpaRepository<TourDetail, Long> {}