package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourMapper;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDTO;
import com.J2EE.TourManagement.Model.DTO.Tour.TourUpdateDTO;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


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
    public Tour handleUpdate(Long id, TourUpdateDTO dto) throws InvalidException {
        Tour existingTour = tourRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy Tour để cập nhật (id = " + id + ")"));

        // Map dữ liệu từ DTO sang entity có sẵn
        tourMapper.updateEntityFromDto(dto, existingTour);

        return tourRepository.save(existingTour);
    }

    //get by id
    public Tour handleGetById(Long id)
            throws InvalidException {
        return tourRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Tour không tồn tại id = " + id + ""));
    }

    public boolean checkIdExists(long id) {
        return this.tourRepository.existsById(id);
    }

}
