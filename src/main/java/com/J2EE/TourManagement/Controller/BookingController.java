package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.DTO.BookingDTO;
import com.J2EE.TourManagement.Model.DTO.BookingResponseDTO;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Service.BookingSer;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.io.InvalidClassException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookingController {
  private static BookingSer bookingSer;
  public BookingController(BookingSer bookingSer) {
    this.bookingSer = bookingSer;
  }

  @PostMapping("/booking/create")
  @ApiMessage("Đặt tour thành công.")
  public ResponseEntity<?>
  postBooking(@RequestBody @Valid BookingDTO newBooking) {
    BookingResponseDTO booking = this.bookingSer.handleSaveBooking(newBooking);

    return ResponseEntity.ok().body(booking);
  }

  @GetMapping("/booking/{id}")
  public ResponseEntity<?> getBookingById(@PathVariable("id") long id)
      throws InvalidException {

    Boolean isIdExist = this.bookingSer.isIdExist(id);
    if (!isIdExist) {
      throw new InvalidException("booking không tồn tại");
    }
    BookingResponseDTO booking = this.bookingSer.getBookingById(id);

    return ResponseEntity.ok().body(booking);
  }

  @GetMapping("/booking")
  public ResponseEntity<?> getAllBooking(@Filter Specification<Booking> spec,
                                         Pageable pageable) {

    return ResponseEntity.ok().body(
        this.bookingSer.getAllBooking(spec, pageable));
  }

  @PutMapping("booking/{id}")
  public ResponseEntity<?> putMethodName(@PathVariable("id") long id,
                                         @RequestBody BookingDTO bookingDTO) throws InvalidException {

    Boolean isIdExist = this.bookingSer.isIdExist(id);
    if (!isIdExist) {
      throw new InvalidException("booking không tồn tại");
    }

    BookingResponseDTO bookingResponseDTO =
        this.bookingSer.updateBooking(id, bookingDTO);
    return ResponseEntity.ok().body(bookingResponseDTO);
  }
}
