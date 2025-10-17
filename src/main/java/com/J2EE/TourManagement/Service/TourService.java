package com.J2EE.TourManagement.Service;

import org.springframework.beans.BeanUtils;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    //them
    public Tour HandleSave(Tour newTour){
        return this.tourRepository.save(newTour);
    }

    //get all
    public List<Tour> HandleGetAll() {
        return this.tourRepository.findAll();
    }

    //get by id
    public Tour handleGetById(Long id) {
        Tour tour = this.tourRepository.findById(id).isPresent() ? this.tourRepository.findById(id).get() : null;
        return tour;
    }

    //sua
    public Tour handleUpdate(Long id, Tour updatedTour) {
        return this.tourRepository.findById(id).
                map(tour -> {
                BeanUtils.copyProperties(updatedTour, tour, "id", "createdAt", "updatedAt");
                return this.tourRepository.save(tour);
                })
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tour có id = " + id));
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
