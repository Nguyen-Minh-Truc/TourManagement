package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.DTO.PaymentDTO;
import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Service.BookingSer;
import com.J2EE.TourManagement.Service.PaymentSer;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1")
public class PaymentController {
  private final PaymentSer paymentSer;
  private final BookingSer bookingSer;

  public PaymentController(PaymentSer paymentSer, BookingSer bookingSer) {
    this.paymentSer = paymentSer;
    this.bookingSer = bookingSer;
  }

  @PostMapping("/payment/create/cash")
  public ResponseEntity<Payment>
  postMethodName(@RequestBody @Valid PaymentDTO paymentDTO)
      throws InvalidException {
        
    return ResponseEntity.ok().body(
        this.paymentSer.createPaymentCash(paymentDTO));
  }

  @PostMapping("/payment/create")
  public ResponseEntity<?>
  createPayment(@Valid @RequestBody PaymentDTO paymentDTO)
      throws InvalidException {
    Booking booking = this.bookingSer.getById(paymentDTO.getId_booking());

    Payment payment = booking.getPayment();
    if (booking.getPayment() != null) {
      throw new InvalidException("Booking đã thanh toán. ");
    }

    try {
      double price = this.bookingSer.getBookingById(paymentDTO.getId_booking())
                         .getTotalPrice();
      long amount = (long)(price);
      String paymentUrl = this.paymentSer.createVNPayPayment(
          amount, "Thanh toán đơn hàng #" + paymentDTO.getId_booking());
      Map<String, Object> response = new HashMap<>();
      response.put("paymentUrl", paymentUrl);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          "Lỗi khi tạo link thanh toán: " + e.getMessage());
    }
  }
  // Callback từ VNPay
  @GetMapping("payment/vnpay_return")
  @ApiMessage("Thanh toán thành công!")
  public ResponseEntity<?>
  vnpayReturn(@RequestParam Map<String, String> params) {
    String responseCode = params.get("vnp_ResponseCode");
    String orderInfo = params.get("vnp_OrderInfo");
    try {
      if ("00".equals(responseCode)) {
        // Lấy id_booking từ orderInfo (ví dụ: "Thanh toán đơn hàng #5")
        long bookingId = extractBookingId(orderInfo);

        Payment payment =
            paymentSer.createPaymentAfterSuccess(bookingId, "VNPAY");
        return ResponseEntity.ok(payment);
      } else {
        return ResponseEntity.badRequest().body(
            "Thanh toán thất bại hoặc bị hủy.");
      }
    } catch (Exception e) {
      return ResponseEntity.internalServerError().body(
          "Lỗi xử lý callback VNPay: " + e.getMessage());
    }
  }
  private long extractBookingId(String orderInfo) {
    try {
      return Long.parseLong(orderInfo.replaceAll("[^0-9]", ""));
    } catch (Exception e) {
      throw new RuntimeException("Không thể lấy ID từ orderInfo: " + orderInfo);
    }
  }
  @GetMapping("/payment/{id}")
  public ResponseEntity<?> getPaymentbyId(@PathVariable("id") long id)
      throws InvalidException {
    if (!this.paymentSer.isIdExist(id)) {
      throw new InvalidException("không tồn tại id: " + id);
    }
    return ResponseEntity.ok(this.paymentSer.getPaymentById(id));
  }
}
