package com.J2EE.TourManagement.Controller;


import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Service.TourService;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<Tour> postNewTour(@Valid @RequestBody Tour newTour) {
        Tour tour = tourService.HandleSave(newTour);
        return ResponseEntity.status(HttpStatus.CREATED).body(tour);
    }

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTour() {
        return ResponseEntity.ok(tourService.HandleGetAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tour> updateTour(@PathVariable Long id, @RequestBody Tour tour) {
        return ResponseEntity.ok(tourService.handleUpdate(id, tour));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Long id) {
        tourService.handleDelete(id);
        return ResponseEntity.noContent().build();
    }
}
