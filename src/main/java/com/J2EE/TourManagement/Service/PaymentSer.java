package com.J2EE.TourManagement.Service;

import org.springframework.stereotype.Service;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Model.PaymentProvider;
import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Model.DTO.PaymentDTO;
import com.J2EE.TourManagement.Repository.BookingRep;
import com.J2EE.TourManagement.Repository.PaymentRep;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import com.J2EE.TourManagement.Util.constan.EnumStatusPayment;
import com.J2EE.TourManagement.Util.error.InvalidException;

@Service
public class PaymentSer {
    private static PaymentRep paymentRep;
    private static BookingRep bookingRep;

    public PaymentSer(PaymentRep paymentRep, BookingRep bookingRep){
        this.paymentRep = paymentRep;
        this.bookingRep = bookingRep;
    }

    public Payment createPayment(PaymentDTO paymentDTO) throws InvalidException{
        Booking booking = this.bookingRep.findById(paymentDTO.getId_booking()).isPresent()
                          ? this.bookingRep.findById(paymentDTO.getId_booking()).get()
                          : null;
        Payment payment = new Payment();
        if(booking.getPayment() != null){
                            throw new InvalidException("Booking đã thanh toán. ");
                          }

        booking.setStatus(EnumStatusBooking.valueOf("COMPLETED"));
        payment.setBooking(booking);
        payment.setMethod(paymentDTO.getMethod());
        payment.setStatus(EnumStatusPayment.valueOf("SUCCESS"));
        return this.paymentRep.save(payment);
    }


    public Payment getPaymentById(long id){
        Payment Payment = this.paymentRep.findById(id).isPresent() ? this.paymentRep.findById(id).get() : null;
        return Payment;
    }

    public boolean isIdExist(long id){
        return this.paymentRep.existsById(id);
    }
}
