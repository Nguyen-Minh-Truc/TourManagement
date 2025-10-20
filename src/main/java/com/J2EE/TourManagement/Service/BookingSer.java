package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.BookingDetail;
import com.J2EE.TourManagement.Model.DTO.BookingDTO;
import com.J2EE.TourManagement.Model.DTO.BookingResponseDTO;
import com.J2EE.TourManagement.Model.DTO.Meta;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Repository.BookingDetailRep;
import com.J2EE.TourManagement.Repository.BookingRep;
import com.J2EE.TourManagement.Util.constan.EnumStatusBooking;
import com.J2EE.TourManagement.Util.error.InvalidException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class BookingSer {
  private static BookingRep bookingRep;
  private static BookingDetailRep bookingDetailRep;
  private static PaymentSer paymentSer;
  private static UserSer userSer;

  public BookingSer(BookingRep bookingRep, BookingDetailRep bookingDetailRep,
                    PaymentSer paymentSer, UserSer userSer) {
    this.bookingRep = bookingRep;
    this.bookingDetailRep = bookingDetailRep;
    this.paymentSer = paymentSer;
    this.userSer = userSer;
  }

  public BookingResponseDTO handleSaveBooking(BookingDTO bookingDTO) {
    Booking booking = new Booking();
    booking.setUser(this.userSer.getUserById(bookingDTO.getUserId()));
    booking.setNote(bookingDTO.getNote());
    booking.setStatus(bookingDTO.getStatus());
    booking.setPayment(
        this.paymentSer.getPaymentById(bookingDTO.getPaymentId()));
    booking.setContactEmail(bookingDTO.getContactEmail());
    booking.setContactPhone(bookingDTO.getContactPhone());
    double totalPrice = 0;
    List<BookingDetail> details = new ArrayList<>();
    for (BookingDetail dtoDetail : bookingDTO.getBookingDetails()) {
      BookingDetail bookingDetail = new BookingDetail();
      bookingDetail.setBooking(booking);
      bookingDetail.setQuantity(dtoDetail.getQuantity());
      bookingDetail.setTourDetailId(dtoDetail.getTourDetailId());
      bookingDetail.setTourPriceId(dtoDetail.getTourPriceId());
      // lỗi chưa fix tour Price
      bookingDetail.setPrice(dtoDetail.getQuantity() * dtoDetail.getPrice());
      bookingDetail.setStatus(true);
      totalPrice += bookingDetail.getPrice();
      details.add(bookingDetail);
    }
    booking.setTotalPrice(totalPrice);
    booking.setBookingDetails(details);

    return new BookingResponseDTO(this.bookingRep.save(booking));
  }

  public BookingResponseDTO getBookingById(long id) {
    Booking booking = this.bookingRep.findById(id).isPresent()
                          ? this.bookingRep.findById(id).get()
                          : null;
    return new BookingResponseDTO(booking);
  }

  public ResultPaginationDTO getAllBooking(Specification<Booking> spec,
                                           Pageable pageable) {
    Page<Booking> pageUser = this.bookingRep.findAll(spec, pageable);
    ResultPaginationDTO result = new ResultPaginationDTO();
    Meta meta = new Meta();

    meta.setPageCurrent(pageable.getPageNumber() + 1);
    meta.setPageSize(pageable.getPageSize());

    meta.setPages(pageUser.getTotalPages());
    meta.setTotal(pageUser.getTotalElements());

    result.setMeta(meta);

    List<BookingResponseDTO> listUser =
        pageUser.getContent()
            .stream()
            .map(item -> new BookingResponseDTO(item))
            .collect(Collectors.toList());

    result.setResult(listUser);

    return result;
  }

  public boolean isIdExist(long id) { return this.bookingRep.existsById(id); }

  public BookingResponseDTO updateBooking(Long bookingId,
                                          BookingDTO bookingDTO) throws InvalidException {
    Booking booking = bookingRep.findById(bookingId).isPresent() ? bookingRep.findById(bookingId).get() : null;

    if (booking.getStatus() != EnumStatusBooking.PENDING) {
      throw new InvalidException(
          "Không thể chỉnh sửa booking khi trạng thái không phải là PENDING");
    }

    booking.setNote(bookingDTO.getNote());
    booking.setStatus(bookingDTO.getStatus());
    booking.setContactEmail(bookingDTO.getContactEmail());
    booking.setContactPhone(bookingDTO.getContactPhone());
    booking.setPayment(paymentSer.getPaymentById(bookingDTO.getPaymentId()));

    // Giữ lại collection cũ (rất quan trọng)
    List<BookingDetail> existingDetails = booking.getBookingDetails();
    existingDetails.clear(); // Xóa phần tử trong list, KHÔNG tạo list mới

    double totalPrice = 0;
    for (BookingDetail dtoDetail : bookingDTO.getBookingDetails()) {
      BookingDetail newDetail = new BookingDetail();
      newDetail.setBooking(booking);
      newDetail.setQuantity(dtoDetail.getQuantity());
      newDetail.setStatus(true);

      newDetail.setTourDetailId(dtoDetail.getTourDetailId());
      newDetail.setTourPriceId(dtoDetail.getTourPriceId());
      // lỗi chưa fix tour Price
      newDetail.setPrice(dtoDetail.getQuantity() * dtoDetail.getPrice());
      newDetail.setStatus(true);
      totalPrice += newDetail.getPrice();
      existingDetails.add(newDetail); 
    }

    booking.setTotalPrice(totalPrice);
    Booking saved = bookingRep.save(booking);
    return new BookingResponseDTO(saved);
  }



}
