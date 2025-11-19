package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.DTO.PaymentDTO;
import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Model.PaymentOrder;
import com.J2EE.TourManagement.Service.BookingSer;
import com.J2EE.TourManagement.Service.PaymentOrderService;
import com.J2EE.TourManagement.Service.PaymentSer;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentOrderService orderService;

    public PaymentController(PaymentOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    @ApiMessage("Tạo đơn thanh toán thành công")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) throws InvalidException, UnsupportedEncodingException {
        PaymentOrder order = orderService.createPendingOrder(paymentRequest.userId, paymentRequest.amount);

        String paymentUrl = orderService.buildVNPayUrl(order);

        Map<String, Object> res = new HashMap<>();
        res.put("paymentUrl", paymentUrl);
        res.put("orderCode", order.getOrderCode());

        return ResponseEntity.ok(res);
    }



    @GetMapping("/vnpay_return")
    public void vnpayReturn(
            @RequestParam Map<String, String> params,
            HttpServletResponse response
    ) throws IOException {

        String responseCode = params.get("vnp_ResponseCode");
        String orderCode = params.get("vnp_TxnRef");

        String redirectUrl;

        try {
            if (!"00".equals(responseCode)) {
                redirectUrl = "http://localhost:5173/payment/vnpay_return?status=fail";
            } else {
                Payment payment = orderService.processVNPaySuccess(orderCode);
                redirectUrl = "http://localhost:5173/payment/vnpay_return?status=success";
            }

        } catch (Exception e) {
            redirectUrl = "http://localhost:5173/payment/vnpay_return?status=error";
        }

        response.sendRedirect(redirectUrl);
    }
}



