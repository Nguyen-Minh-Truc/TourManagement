package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Util.error.InvalidException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    //Create
    @Transactional
    public Tour handleSave(Tour tour) {
        if (tour.getTourDetails() != null) {
            for (TourDetail detail : tour.getTourDetails()) {
                detail.setTour(tour); // Gắn tour_id
            }
        }
        return tourRepository.save(tour);
    }


    //Read
    public List<Tour> handleGetAll() {
        return this.tourRepository.findAll();
    }

    //Update
    @Transactional
    public Tour handleUpdate(Long id, Tour updatedTour)  throws InvalidException {
        if (!tourRepository.existsById(id))
        {
            throw new InvalidException("Không tìm thấy TourPrice để xóa (id = " + id + ")");
        }
        return this.tourRepository.findById(id).
                map(tour -> {
                    BeanUtils.copyProperties(updatedTour, tour, "id", "createdAt", "tourDetails");
                    return this.tourRepository.save(tour);
                }).get();
    }

    //get by id
    public Optional<Tour> handleGetById(Long id) {
        return this.tourRepository.findById(id);
    }

    public Page<Tour> getPagedTours(String status, String destination, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Nếu người dùng không nhập filter, thay bằng chuỗi rỗng để match tất cả
        String statusFilter = (status == null || status.isEmpty()) ? "" : status;
        String destinationFilter = (destination == null || destination.isEmpty()) ? "" : destination;

        return tourRepository.findByStatusContainingIgnoreCaseAndDestinationContainingIgnoreCase(
                statusFilter, destinationFilter, pageable
        );
    }
}
