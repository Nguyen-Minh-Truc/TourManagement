package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.TourItinerary.*;
import com.J2EE.TourManagement.Model.TourItinerary;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourItineraryMapper {

    // Entity -> ResponseDTO
    TourItineraryDTO toResponseDTO(TourItinerary itinerary);

    List<TourItineraryDTO> toResponseDTOList(List<TourItinerary> itineraries);

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
