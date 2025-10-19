package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.DTO.Tour.TourDetailDTO;
import com.J2EE.TourManagement.Model.DTO.Tour.TourPriceDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Service.TourPriceService;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tour_details")
public class TourPriceController {

    private final TourPriceService tourPriceService;

    public TourPriceController(TourPriceService tourPriceService) {
        this.tourPriceService = tourPriceService;
    }

    @GetMapping("/{id}/prices")
    public ResponseEntity<List<TourPriceDTO>> fetchTourPriceByTourId(@PathVariable Long id) throws InvalidException {
        return ResponseEntity.ok(tourPriceService.handleGetAll(id));
    }

    @ApiMessage("Thêm tour price thành công!")
    @PostMapping("/prices")
    public ResponseEntity<TourPrice> create(@Valid @RequestBody TourPrice price) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourPriceService.handleSave(price));
    }

    @ApiMessage("Sửa tour price thành công!")
    @PutMapping("/prices/{id}")
    public ResponseEntity<TourPrice> update(@PathVariable Long id, @Valid @RequestBody TourPrice price) throws InvalidException {
        return ResponseEntity.ok(tourPriceService.handleUpdate(id, price));
    }

}
