package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourPriceMapper;
import com.J2EE.TourManagement.Model.DTO.Tour.TourPriceDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Repository.TourPriceRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TourPriceService {

    private final TourPriceRepository tourPriceRepository;
    private final TourDetailRepository tourDetailRepository;
    private final TourPriceMapper tourPriceMapper;

    //Create
    public TourPrice handleSave(TourPrice price) {
        return tourPriceRepository.save(price);
    }

    //Read by TourPrice id
    public List<TourPriceDTO> handleGetAll(Long id) throws InvalidException {
        Optional<TourDetail> opt = tourDetailRepository.findById(id);

        if (opt.isEmpty()) {
            throw new InvalidException("Không tìm thấy TourDetailId để getall (id = " + id-- + ")");
        }

        List<TourPrice> price = opt.get().getTourPrices();

        return tourPriceMapper.toDTOList(price);
    }

    //Update
    public TourPrice handleUpdate(Long id, TourPrice updated) throws InvalidException {
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

    public TourPrice getTourPriceById(long id){
        TourPrice tourPrice = this.tourPriceRepository.findById(id).isPresent() ? this.tourPriceRepository.findById(id).get() : null;
        return tourPrice;
    }

}
