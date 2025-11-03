package com.J2EE.TourManagement.Controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
import org.springframework.beans.factory.annotation.Value;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/api/v1")
public class PaymentController {
    private final PaymentSer paymentSer;
    private final BookingSer bookingSer;

     @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.url}")
    private String vnp_Url;

    @Value("${vnpay.returnUrl}")
    private String vnp_ReturnUrl;

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

    @GetMapping("/payment/create")
    public ResponseEntity<?> createPayment(@RequestParam("amount") long amount) throws UnsupportedEncodingException {
        String vnp_TxnRef = String.valueOf(System.currentTimeMillis());
        String vnp_OrderInfo = "Thanh toan don hang: " + vnp_TxnRef;
        String vnp_IpAddr = "127.0.0.1";
        String vnp_CreateDate = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (!fieldName.equals(fieldNames.get(fieldNames.size() - 1))) {
                    hashData.append('&');
                    query.append('&');
                }
            }
        }

        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnp_Url + "?" + query.toString();

        return ResponseEntity.ok(Map.of("paymentUrl", paymentUrl));
    }

    private static String hmacSHA512(String key, String data) {
        try {
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac512.init(secretKey);
            byte[] bytes = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder();
            for (byte b : bytes) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}
