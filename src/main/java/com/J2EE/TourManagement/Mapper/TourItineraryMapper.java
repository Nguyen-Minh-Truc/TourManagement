package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.TourItinerary.*;
import com.J2EE.TourManagement.Model.TourItinerary;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourItineraryMapper {

    // Entity -> ResponseDTO
    @Mapping(target = "tourDetailId", source = "tourDetail.id")
    TourItineraryResponseDTO toResponseDTO(TourItinerary itinerary);

    List<TourItineraryResponseDTO> toResponseDTOList(List<TourItinerary> itineraries);

    // CreateDTO -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tourDetail", ignore = true) // set thủ công trong service
    TourItinerary toEntity(TourItineraryCreateDTO dto);

    // UpdateDTO -> cập nhật entity hiện có
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "tourDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(TourItineraryUpdateDTO dto, @MappingTarget TourItinerary itinerary);
}
