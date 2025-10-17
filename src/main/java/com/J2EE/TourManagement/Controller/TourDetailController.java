package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Service.TourDetailService;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour/details")
public class TourDetailController {

    private final TourDetailService tourDetailService;

    public TourDetailController(TourDetailService tourDetailService) {
        this.tourDetailService = tourDetailService;
    }

    @PostMapping
    public ResponseEntity<TourDetail> create(@Valid @RequestBody TourDetail detail) throws InvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourDetailService.save(detail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TourDetail> update(@PathVariable Long id, @Valid @RequestBody TourDetail detail) throws InvalidException {
        return ResponseEntity.ok(tourDetailService.update(id, detail));
    }

}
