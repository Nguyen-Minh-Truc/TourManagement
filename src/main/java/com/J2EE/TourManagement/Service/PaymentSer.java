package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.DTO.PaymentDTO;
import com.J2EE.TourManagement.Model.Payment;
import com.J2EE.TourManagement.Repository.BookingRep;
import com.J2EE.TourManagement.Repository.PaymentRep;
import com.J2EE.TourManagement.Util.VNPayConfig;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import com.J2EE.TourManagement.Util.constan.EnumStatusPayment;
import com.J2EE.TourManagement.Util.error.InvalidException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentSer {
  private final PaymentRep paymentRep;
  private final BookingRep bookingRep;

  @Value("${vnpay.tmnCode}") private String vnp_TmnCode;

  @Value("${vnpay.hashSecret}") private String vnp_HashSecret;

  @Value("${vnpay.url}") private String vnp_Url;

  @Value("${vnpay.returnUrl}") private String vnp_ReturnUrl;

  public PaymentSer(PaymentRep paymentRep, BookingRep bookingRep) {
    this.paymentRep = paymentRep;
    this.bookingRep = bookingRep;
  }

  public Payment createPaymentCash(PaymentDTO paymentDTO)
      throws InvalidException {
    Booking booking =
        this.bookingRep.findById(paymentDTO.getId_booking()).isPresent()
            ? this.bookingRep.findById(paymentDTO.getId_booking()).get()
            : null;
    Payment payment = new Payment();
    if (booking.getPayment() != null) {
      throw new InvalidException("Booking đã thanh toán. ");
    }
    booking.setStatus(EnumStatusBooking.valueOf("COMPLETED"));
    payment.setBooking(booking);
    payment.setAmount(booking.getTotalPrice());
    payment.setMethod("Cash");
    payment.setStatus(EnumStatusPayment.valueOf("SUCCESS"));
    return this.paymentRep.save(payment);
  }

  public Payment getPaymentById(long id) {
    Payment Payment = this.paymentRep.findById(id).isPresent()
                          ? this.paymentRep.findById(id).get()
                          : null;
    return Payment;
  }

  public boolean isIdExist(long id) { return this.paymentRep.existsById(id); }

  public String createVNPayPayment(long amount, String orderInfo)
      throws UnsupportedEncodingException {
    String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
    String vnp_IpAddr = "127.0.0.1";
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", "2.1.0");
    vnp_Params.put("vnp_Command", "pay");
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put(
        "vnp_Amount",
        String.valueOf(amount *
                       100)); // nhân 100 vì VNPay yêu cầu đơn vị là VND * 100
    vnp_Params.put("vnp_CurrCode", "VND");
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", orderInfo);
    vnp_Params.put("vnp_OrderType", "other");
    vnp_Params.put("vnp_Locale", "vn");
    vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    Date createDate = cld.getTime();
    String vnp_CreateDate =
        new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(createDate);
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    // Build query và hash
    String query = VNPayConfig.buildUrl(vnp_Params);
    String vnp_SecureHash = hmacSHA512(vnp_HashSecret, query);
    String paymentUrl =
        vnp_Url + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;

    return paymentUrl;
  }

  private String hmacSHA512(String key, String data) {
    try {
      javax.crypto.Mac hmac512 = javax.crypto.Mac.getInstance("HmacSHA512");
      javax.crypto.spec.SecretKeySpec secretKey =
          new javax.crypto.spec.SecretKeySpec(key.getBytes("UTF-8"),
                                              "HmacSHA512");
      hmac512.init(secretKey);
      byte[] bytes = hmac512.doFinal(data.getBytes("UTF-8"));
      StringBuilder hash = new StringBuilder();
      for (byte b : bytes) {
        hash.append(String.format("%02x", b));
      }
      return hash.toString();
    } catch (Exception ex) {
      throw new RuntimeException("Lỗi khi tạo chữ ký VNPay", ex);
    }
  }
  public Payment createPaymentAfterSuccess(long bookingId, String method)
      throws InvalidException {
    Booking booking = bookingRep.findById(bookingId).orElseThrow(
        () -> new InvalidException("Không tìm thấy booking id: " + bookingId));

    // Nếu booking đã có payment rồi thì bỏ qua
    if (booking.getPayment() != null) {
      throw new InvalidException("Booking này đã có thanh toán rồi.");
    }

    Payment payment = new Payment();
    payment.setBooking(booking);
    payment.setMethod(method);
    payment.setAmount(booking.getTotalPrice());
    payment.setStatus(EnumStatusPayment.SUCCESS);
    // Lưu payment
    paymentRep.save(payment);

    // Cập nhật trạng thái booking
    booking.setStatus(EnumStatusBooking.COMPLETED);
    booking.setPayment(payment);
    bookingRep.save(booking);

    return payment;
  }
}
