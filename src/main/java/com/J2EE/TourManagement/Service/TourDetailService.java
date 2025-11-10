package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourDetailMapper;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailUpdateDTO;
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
    public TourDetail handleSave(TourDetailCreateDTO dto)
            throws InvalidException {
        if (!tourRepository.existsById(dto.getTourId())) {
            throw new InvalidException(
                    "Không tìm thấy TourId để thêm (id = " + dto.getTourId() + ")");
        }

        TourDetail detail = tourDetailMapper.toEntity(dto);

        return tourDetailRepository.save(detail);
    }

    // Update
    public TourDetail handleUpdate(Long id, TourDetailUpdateDTO dto)
            throws InvalidException {
        TourDetail existing = tourDetailRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy TourDetail để cập nhật (id = " + id + ")"));

        // Map dữ liệu từ DTO sang entity có sẵn
        tourDetailMapper.updateEntityFromDto(dto, existing);

        return tourDetailRepository.save(existing);
    }

    public TourDetail getTourDetailById(long id) {
        TourDetail tourDetail = this.tourDetailRepository.findById(id).isPresent()
                ? this.tourDetailRepository.findById(id).get()
                : null;
        return tourDetail;
    }
}
