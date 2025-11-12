package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.Tour.TourUpdateDTO;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceCreateDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDTO;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.TourPrice;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TourDetailMapper.class})
public interface TourMapper {

    TourDTO toDTO(Tour tour);

    List<TourDTO> toDTOList(List<Tour> tours);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "updatedBy", ignore = true),
            @Mapping(target = "tourDetails", ignore = true)
    })
    void updateEntityFromDto(TourUpdateDTO dto, @MappingTarget Tour entity);

    List<TourDetail> toTourDetailEntities(List<TourDetailCreateDTO> dtoList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tourDetail", ignore = true)
    List<TourPrice> toTourPriceEntities(List<TourPriceCreateDTO> dtoList);
}