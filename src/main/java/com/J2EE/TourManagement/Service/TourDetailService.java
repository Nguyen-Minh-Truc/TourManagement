package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourDetailMapper;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Repository.TourRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TourDetailService {

    private final TourDetailMapper tourDetailMapper;
    private final TourRepository tourRepository;
    private final TourDetailRepository tourDetailRepository;

    public List<TourDetailDTO> handleGetAll(Long tourId) throws InvalidException {

        Optional<Tour> tourOpt = tourRepository.findById(tourId);
        if (tourOpt.isEmpty()) {
            throw new InvalidException(
                    "Không tìm thấy TourId để detall (id = " + tourId + ")");
        }

        List<TourDetail> details = tourOpt.get().getTourDetails();

        // Map entity -> DTO
        return tourDetailMapper.toDTOList(details);
    }

    // Create
    public TourDetail handleSave(TourDetail detail) throws InvalidException {
        Long tourId = detail.getTour().getId();
        if (!tourRepository.existsById(tourId)) {
            throw new InvalidException(
                    "Không tìm thấy TourId để thêm (id = " + tourId + ")");
        }
        return tourDetailRepository.save(detail);
    }

    // Update
    public TourDetail handleUpdate(Long id, TourDetail updated)
            throws InvalidException {
        if (!tourDetailRepository.existsById(id)) {
            throw new InvalidException("Không tìm thấy TourDetail ID = " + id);
        }

        return tourDetailRepository.findById(id)
                .map(existing -> {
                    BeanUtils.copyProperties(updated, existing, "id", "tour", "createdAt",
                            "tourPrices");
                    return tourDetailRepository.save(existing);
                })
                .get();
    }

    public TourDetail getTourDetailById(long id) {
        TourDetail tourDetail = this.tourDetailRepository.findById(id).isPresent()
                ? this.tourDetailRepository.findById(id).get()
                : null;
        return tourDetail;
    }
}
