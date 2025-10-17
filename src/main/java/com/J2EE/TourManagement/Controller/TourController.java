package com.J2EE.TourManagement.Controller;


import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Service.TourService;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tours")
public class TourController {
    private final TourService tourService;

    public TourController (TourService tourService) {
        this.tourService = tourService;
    }

    //Create
    @PostMapping
    public ResponseEntity<Tour> postNewTour(@Valid @RequestBody Tour newTour) {
        Tour tour = tourService.handleSave(newTour);
        return ResponseEntity.status(HttpStatus.CREATED).body(tour);
    }

    //Read
    @GetMapping
    public ResponseEntity<List<Tour>> getAllTour() {
        return ResponseEntity.ok(tourService.handleGetAll());
    }

    //Update
    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @Valid @RequestBody Tour tour) throws InvalidException {
        return ResponseEntity.ok(tourService.handleUpdate(id, tour));
    }

    //Read by id
    @GetMapping("/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id) {
        return tourService.handleGetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Filter
    @GetMapping("/filter")
    public ResponseEntity<Page<Tour>> getFilteredTours(
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "") String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<Tour> result = tourService.getPagedTours(status, destination, page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
