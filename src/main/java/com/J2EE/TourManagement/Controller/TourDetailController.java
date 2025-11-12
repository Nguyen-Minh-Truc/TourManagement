package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Mapper.TourDetailMapper;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailUpdateDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Service.TourDetailService;
import com.J2EE.TourManagement.Util.annotation.ApiMessage;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tours")
public class TourDetailController {

    private final TourDetailService tourDetailService;
    private final TourDetailMapper tourDetailMapper;

    public TourDetailController(TourDetailService tourDetailService, TourDetailMapper tourDetailMapper) {
        this.tourDetailService = tourDetailService;
        this.tourDetailMapper = tourDetailMapper;
    }

    //Create
    @ApiMessage("Thêm tour detail thành công!")
    @PostMapping("/details")
    public ResponseEntity<TourDetailDTO> create(@Valid @RequestBody TourDetailCreateDTO dto)
            throws InvalidException {
        TourDetail detail = tourDetailService.handleSave(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tourDetailMapper.toDTO(detail));
    }

    //Read by tour id
    @GetMapping("/{tourId}/details")
    public ResponseEntity<List<TourDetailDTO>> fetchTourDetailByTourId(@PathVariable("tourId") Long tourId) throws InvalidException {
        return ResponseEntity.ok(tourDetailService.handleGetAll(tourId));
    }

    //Update
    @ApiMessage("Sửa tour detail thành công!")
    @PutMapping("/details/{id}")
    public ResponseEntity<TourDetailDTO> update(@PathVariable Long id, @Valid @RequestBody TourDetailUpdateDTO dto)
            throws InvalidException {
        TourDetail reponse = tourDetailService.handleUpdate(id, dto);
        return ResponseEntity.ok(tourDetailMapper.toDTO(reponse));
    }
}
