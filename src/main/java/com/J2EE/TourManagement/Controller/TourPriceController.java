package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Service.TourPriceService;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour/prices")
public class TourPriceController {

    private final TourPriceService tourPriceService;

    public TourPriceController(TourPriceService tourPriceService) {
        this.tourPriceService = tourPriceService;
    }

    @PostMapping
    public ResponseEntity<TourPrice> create(@Valid @RequestBody TourPrice price) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourPriceService.save(price));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourPrice> update(@PathVariable Long id, @Valid @RequestBody TourPrice price) throws InvalidException {
        return ResponseEntity.ok(tourPriceService.update(id, price));
    }

}
