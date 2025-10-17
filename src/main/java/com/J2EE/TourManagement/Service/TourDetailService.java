package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourDetailService {

    private final TourDetailRepository tourDetailRepository;

    public TourDetailService(TourDetailRepository tourDetailRepository) {
        this.tourDetailRepository = tourDetailRepository;
    }

    //Create
    public TourDetail save(TourDetail detail) throws InvalidException {
        Long tourId = detail.getTour().getId();
        if (!tourDetailRepository.existsById(tourId))
        {
            throw new InvalidException("Không tìm thấy TourId để thêm (id = " + tourId + ")");
        }
        return tourDetailRepository.save(detail);
    }

    //Update
    public TourDetail update(Long id, TourDetail updated) throws InvalidException {
        if (!tourDetailRepository.existsById(id))
        {
            throw new InvalidException("Không tìm thấy TourDetail ID = " + id);
        }

        return tourDetailRepository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(updated, existing, "id", "createdAt", "tourPrices");
                    return tourDetailRepository.save(existing);
                }).get();
    }

}
