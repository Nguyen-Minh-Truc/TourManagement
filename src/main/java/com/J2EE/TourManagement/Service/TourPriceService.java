package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Repository.TourPriceRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourPriceService {

    private final TourPriceRepository tourPriceRepository;

    public TourPriceService(TourPriceRepository tourPriceRepository) {
        this.tourPriceRepository = tourPriceRepository;
    }

    //Create
    public TourPrice save(TourPrice price) {
        return tourPriceRepository.save(price);
    }

    //Update
    public TourPrice update(Long id, TourPrice updated) throws InvalidException {
        if (!tourPriceRepository.existsById(id))
        {
            throw new InvalidException("Không tìm thấy TourPrice ID = " + id);
        }

        return tourPriceRepository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(updated, existing, "id");
                    return tourPriceRepository.save(existing);
                }).get();
    }

}
