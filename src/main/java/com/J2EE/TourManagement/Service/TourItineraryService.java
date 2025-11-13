package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.TourItineraryMapper;
import com.J2EE.TourManagement.Model.DTO.ResultPaginationDTO;
import com.J2EE.TourManagement.Model.DTO.TourItinerary.TourItineraryCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourItinerary.TourItineraryDTO;
import com.J2EE.TourManagement.Model.DTO.TourItinerary.TourItineraryUpdateDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.TourItinerary;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Repository.TourItineraryRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TourItineraryService {
    private final TourItineraryRepository tourItineraryRepository;
    private final TourItineraryMapper tourItineraryMapper;
    private final TourDetailRepository tourDetailRepository;

    //Create
    public TourItinerary handleSave(TourItineraryCreateDTO dto)
            throws InvalidException {
        TourDetail tourDetail = tourDetailRepository.findById(dto.getTourDetailId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy TourDetail với id = " + dto.getTourDetailId()));

        if (tourDetail.getItinerary() != null)
        {
            throw new InvalidException("Lộ trình tour đã tồn tại.");
        }

        TourItinerary itinerary = tourItineraryMapper.toEntity(dto);
        itinerary.setTourDetail(tourDetail);

        return tourItineraryRepository.save(itinerary);
    }

    //Read all
    public ResultPaginationDTO handleGetAll(Specification<TourItinerary> spec, Pageable pageable) {
        Page<TourItinerary> tourItineraries = tourItineraryRepository.findAll(spec, pageable);

        Page<TourItineraryDTO> dtoPage = tourItineraries.map(tourItineraryMapper::toResponseDTO);

        return PaginationUtils.build(dtoPage, pageable);
    }

    //Update
    public TourItinerary handleUpdate (Long id, TourItineraryUpdateDTO dto) throws InvalidException {
        TourItinerary existing = tourItineraryRepository.findById(id)
                .orElseThrow(() -> new InvalidException("Không tìm thấy TourItinerary để cập nhật (id = " + id + ")"));

        // Map du lieu tu DTO sang entity
        tourItineraryMapper.updateEntityFromDTO(dto, existing);

        // Gán tourDetail id cho Itinerary
        if (dto.getTourDetailId() != null) {
            TourDetail detail = tourDetailRepository.findById(dto.getTourDetailId())
                    .orElseThrow(() -> new InvalidException(
                            "Không tìm thấy TourDetail với id = " + dto.getTourDetailId()));
            if (detail.getItinerary() != null && detail.getItinerary().getId() != id)
            {
                throw new InvalidException("TourDetail id = " + dto.getTourDetailId() +" đã có lộ trình tour rồi!");
            }
            existing.setTourDetail(detail);
        }

        return tourItineraryRepository.save(existing);
    }
}
