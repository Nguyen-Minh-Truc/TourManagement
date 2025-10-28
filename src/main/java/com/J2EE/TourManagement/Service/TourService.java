package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourMapper;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDTO;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    //Create
    @Transactional
    public Tour handleSave(Tour tour) {
        return tourRepository.save(tour);
    }

    //Read
    public ResultPaginationDTO handleGetAll(Specification<Tour> spec, Pageable pageable) {
        Page<Tour> tours = tourRepository.findAll(spec, pageable);

        Page<TourDTO> dtoPage = tours.map(tourMapper::toDTO);

        return PaginationUtils.build(dtoPage, pageable);

    }

    //Update
    @Transactional
    public Tour handleUpdate(Long id, Tour updatedTour)  throws InvalidException {
        if (!tourRepository.existsById(id))
        {
            throw new InvalidException("Không tìm thấy Tour để cập nhật (id = " + id + ")");
        }
        return this.tourRepository.findById(id).
                map(tour -> {
                    BeanUtils.copyProperties(updatedTour, tour, "id", "createdAt", "tourDetails");
                    return this.tourRepository.save(tour);
                }).get();
    }

    //get by id

    public Tour handleGetById(Long id) {

        Tour tour = this.tourRepository.findById(id).isPresent() ? this.tourRepository.findById(id).get() : null;
        return tour;

    }



    //xoa
    public void  handleDelete(Long id) {
        if (!this.tourRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy tour để xóa (id = " + id + ")");
        }
        this.tourRepository.deleteById(id);
    }

    public boolean checkIdExists(long id){
        return this.tourRepository.existsById(id);
    }

}
