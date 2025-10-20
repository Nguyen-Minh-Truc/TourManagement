package com.J2EE.TourManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.J2EE.TourManagement.Model.PaymentProvider;

@Repository
public interface PaymentProviderRep extends JpaRepository<PaymentProvider, Long>  {
    
}
