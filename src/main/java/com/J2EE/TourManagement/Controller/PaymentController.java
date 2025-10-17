package com.J2EE.TourManagement.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Model.DTO.PaymentDTO;
import com.J2EE.TourManagement.Service.BookingSer;
import com.J2EE.TourManagement.Service.PaymentSer;
import com.J2EE.TourManagement.Util.error.InvalidException;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/api/v1")
public class PaymentController {
    private static PaymentSer paymentSer;
    private static BookingSer bookingSer;
    public PaymentController(PaymentSer paymentSer, BookingSer bookingSer){
         this.paymentSer = paymentSer;
         this.bookingSer = bookingSer;
    }
    
    @PostMapping("/payment/create")
    public ResponseEntity<Payment> postMethodName(@RequestBody @Valid PaymentDTO paymentDTO) throws InvalidException {
        return ResponseEntity.ok().body(this.paymentSer.createPayment(paymentDTO));
    }
    
    @GetMapping("/payment/{id}")
    public ResponseEntity<?> getPaymentbyId(@PathVariable("id") long id) throws InvalidException {
        if(!this.paymentSer.isIdExist(id)){
            throw new InvalidException("không tồn tại id: " + id);
        }
        return ResponseEntity.ok(this.paymentSer.getPaymentById(id));

    }
    


    @GetMapping("/payment")
    public String getAllPayment(@RequestParam String param) {
        return new String();
    }
    
}
