package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.Tour.TourUpdateDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailUpdateDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TourPriceMapper.class})
public interface TourDetailMapper {

    TourDetailDTO toDTO(TourDetail tourDetail);
    List<TourDetailDTO> toDTOList(List<TourDetail> tourDetails);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tour", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "tourPrices", ignore = true),
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "itinerary", ignore = true)
    })
    TourDetail toEntity(TourDetailCreateDTO tourDetailCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tour", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updateAt", ignore = true),
            @Mapping(target = "tourPrices", ignore = true),
            @Mapping(target = "reviews", ignore = true),
            @Mapping(target = "itinerary", ignore = true)
    })
    void updateEntityFromDto(TourDetailUpdateDTO dto, @MappingTarget TourDetail entity);
}