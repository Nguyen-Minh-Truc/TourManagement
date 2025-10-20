package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDetailDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Service.TourDetailService;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tours")
public class TourDetailController {

    private final TourDetailService tourDetailService;

    public TourDetailController(TourDetailService tourDetailService) {
        this.tourDetailService = tourDetailService;
    }

    //Read by tour id
    @GetMapping("/{tourId}/details")
    public ResponseEntity<List<TourDetailDTO>> fetchTourDetailByTourId(@PathVariable Long tourId) throws InvalidException {
        return ResponseEntity.ok(tourDetailService.handleGetAll(tourId));
    }

    @ApiMessage("Thêm tour detail thành công!")
    @PostMapping("/details")
    public ResponseEntity<TourDetail> create(@Valid @RequestBody TourDetail detail) throws InvalidException {
        return ResponseEntity.status(HttpStatus.CREATED).body(tourDetailService.handleSave(detail));
    }

    @ApiMessage("Sửa tour detail thành công!")
    @PutMapping("/details/{id}")
    public ResponseEntity<TourDetail> update(@PathVariable Long id, @Valid @RequestBody TourDetail detail) throws InvalidException {
        return ResponseEntity.ok(tourDetailService.handleUpdate(id, detail));
    }

}
