package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.Booking;
import com.J2EE.TourManagement.Model.User;
import com.J2EE.TourManagement.Model.DTO.BookingDTO;
import com.J2EE.TourManagement.Model.DTO.BookingResponseDTO;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Service.BookingSer;
import com.J2EE.TourManagement.Service.UserSer;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import java.io.InvalidClassException;
import java.util.List;
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
  private final BookingSer bookingSer;
  private final UserSer userSer;
  public BookingController(BookingSer bookingSer, UserSer userSer) {
    this.bookingSer = bookingSer;
    this.userSer = userSer;
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
                                         @RequestBody BookingDTO bookingDTO)
      throws InvalidException {

    Boolean isIdExist = this.bookingSer.isIdExist(id);
    if (!isIdExist) {
      throw new InvalidException("booking không tồn tại");
    }

    BookingResponseDTO bookingResponseDTO =
        this.bookingSer.updateBooking(id, bookingDTO);
    return ResponseEntity.ok().body(bookingResponseDTO);
  }

  @GetMapping("/booking/user/{id}")
  public ResponseEntity<?> getBookingByUser(@PathVariable("id") long id) {
    User user = this.userSer.getUserById(id);
    List<Booking> listBookings = this.bookingSer.getBookingByUser(user);
    return ResponseEntity.ok().body(listBookings);
  }
}
