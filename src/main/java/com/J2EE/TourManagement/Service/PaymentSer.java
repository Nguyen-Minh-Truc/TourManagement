package com.J2EE.TourManagement.Service;

import org.springframework.stereotype.Service;

import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Repository.PaymentRep;

@Service
public class PaymentSer {
    private static PaymentRep paymentRep;
    public PaymentSer(PaymentRep paymentRep){
        this.paymentRep = paymentRep;
    }

    public Payment getPaymentById(long id){
        Payment Payment = this.paymentRep.findById(id).isPresent() ? this.paymentRep.findById(id).get() : null;
        return Payment;
    }
}
