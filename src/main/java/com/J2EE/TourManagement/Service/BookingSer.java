package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.BookingDetail;
import com.J2EE.TourManagement.Model.DTO.BookingDTO;
import com.J2EE.TourManagement.Model.DTO.BookingDetailDTO;
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

  private final BookingRep bookingRep;
  private final BookingDetailRep bookingDetailRep;
  private final PaymentSer paymentSer;
  private final UserSer userSer;
  private final TourDetailService tourDetailSer;
  private final TourPriceService tourPriceSer;

  public BookingSer(BookingRep bookingRep, BookingDetailRep bookingDetailRep,
                    PaymentSer paymentSer, UserSer userSer,
                    TourDetailService tourDetailSer,
                    TourPriceService tourPriceSer) {
    this.bookingRep = bookingRep;
    this.bookingDetailRep = bookingDetailRep;
    this.paymentSer = paymentSer;
    this.userSer = userSer;
    this.tourDetailSer = tourDetailSer;
    this.tourPriceSer = tourPriceSer;
  }

  // ===================== CREATE =====================
  public BookingResponseDTO handleSaveBooking(BookingDTO bookingDTO) {
    Booking booking = new Booking();

    booking.setUser(userSer.getUserById(bookingDTO.getUserId()));
    booking.setNote(bookingDTO.getNote());
    booking.setStatus(bookingDTO.getStatus());
    booking.setPayment(paymentSer.getPaymentById(bookingDTO.getPaymentId()));
    booking.setContactEmail(bookingDTO.getContactEmail());
    booking.setContactPhone(bookingDTO.getContactPhone());

    double totalPrice = 0;
    List<BookingDetail> details = new ArrayList<>();

    for (BookingDetailDTO dto : bookingDTO.getBookingDetails()) {
      BookingDetail bookingDetail = new BookingDetail();
      bookingDetail.setBooking(booking);
      bookingDetail.setQuantity(dto.getQuantity());
      bookingDetail.setStatus(true);

      bookingDetail.setTourDetail(
          tourDetailSer.getTourDetailById(dto.getTourDetailId()));
      bookingDetail.setTourPrice(
          tourPriceSer.getTourPriceById(dto.getTourPriceId()));

      double unitPrice = tourPriceSer.getTourPriceById(dto.getTourPriceId())
                             .getPrice()
                             .doubleValue();
      double price = dto.getQuantity() * unitPrice;

      bookingDetail.setPrice(price);

      totalPrice += price;

      details.add(bookingDetail);
    }

    booking.setTotalPrice(totalPrice);
    booking.setBookingDetails(details);

    Booking savedBooking = bookingRep.save(booking);
    return new BookingResponseDTO(savedBooking);
  }

  // ===================== UPDATE =====================
  public BookingResponseDTO updateBooking(Long bookingId, BookingDTO bookingDTO)
      throws InvalidException {
    Booking booking = bookingRep.findById(bookingId).orElseThrow(
        () -> new InvalidException("Booking không tồn tại"));

    if (booking.getStatus() != EnumStatusBooking.PENDING) {
      throw new InvalidException(
          "Không thể chỉnh sửa booking khi trạng thái không phải là PENDING");
    }

    booking.setNote(bookingDTO.getNote());
    booking.setStatus(bookingDTO.getStatus());
    booking.setContactEmail(bookingDTO.getContactEmail());
    booking.setContactPhone(bookingDTO.getContactPhone());
    booking.setPayment(paymentSer.getPaymentById(bookingDTO.getPaymentId()));

    booking.getBookingDetails().clear();

    double totalPrice = 0;
    List<BookingDetail> newDetails = new ArrayList<>();

    for (BookingDetailDTO dto : bookingDTO.getBookingDetails()) {
      BookingDetail detail = new BookingDetail();
      detail.setBooking(booking);
      detail.setQuantity(dto.getQuantity());

      detail.setPrice(dto.getQuantity() * dto.getPrice());
      detail.setStatus(true);

      detail.setTourDetail(
          tourDetailSer.getTourDetailById(dto.getTourDetailId()));
      detail.setTourPrice(tourPriceSer.getTourPriceById(dto.getTourPriceId()));

      double unitPrice = tourPriceSer.getTourPriceById(dto.getTourPriceId())
                             .getPrice()
                             .doubleValue();
      double price = dto.getQuantity() * unitPrice;

      detail.setPrice(price);

      totalPrice += detail.getPrice();
      newDetails.add(detail);
    }

    booking.setTotalPrice(totalPrice);
    booking.getBookingDetails().addAll(newDetails);

    Booking saved = bookingRep.save(booking);
    return new BookingResponseDTO(saved);
  }

  // ===================== READ =====================
  public BookingResponseDTO getBookingById(long id) {
    Booking booking = bookingRep.findById(id).orElseThrow(
        () -> new RuntimeException("Booking không tồn tại"));
    return new BookingResponseDTO(booking);
  }

  public ResultPaginationDTO getAllBooking(Specification<Booking> spec,
                                           Pageable pageable) {
    Page<Booking> pageUser = bookingRep.findAll(spec, pageable);
    Meta meta = new Meta();
    meta.setPageCurrent(pageable.getPageNumber() + 1);
    meta.setPageSize(pageable.getPageSize());
    meta.setPages(pageUser.getTotalPages());
    meta.setTotal(pageUser.getTotalElements());

    ResultPaginationDTO result = new ResultPaginationDTO();
    result.setMeta(meta);

    List<BookingResponseDTO> listUser = pageUser.getContent()
                                            .stream()
                                            .map(BookingResponseDTO::new)
                                            .collect(Collectors.toList());
    result.setResult(listUser);

    return result;
  }

  public boolean isIdExist(long id) { return bookingRep.existsById(id); }
}
