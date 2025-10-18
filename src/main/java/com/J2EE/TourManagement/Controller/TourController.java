package com.J2EE.TourManagement.Controller;


import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Service.TourService;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tours")
public class TourController {
    private final TourService tourService;

    public TourController (TourService tourService) {
        this.tourService = tourService;
    }

    //Create
    @PostMapping
    @ApiMessage("Thêm tour thành công!")
    public ResponseEntity<Tour> postNewTour(@Valid @RequestBody Tour newTour) {
        Tour tour = tourService.handleSave(newTour);
        return ResponseEntity.status(HttpStatus.CREATED).body(tour);
    }

    //Read
    @GetMapping
    public ResponseEntity<ResultPaginationDTO> fetchAllTour(@Filter Specification<Tour> spec, Pageable pageable) {
        return ResponseEntity.ok(tourService.handleGetAll(spec, pageable));
    }

    //Update
    @ApiMessage("Sửa tour thành công!")
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @Valid @RequestBody Tour tour) throws InvalidException {
        return ResponseEntity.ok(tourService.handleUpdate(id, tour));
    }

    //Read by id
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) throws InvalidException{
        return tourService.handleGetById(id)
                .map(ResponseEntity::ok).get();
    }

}
