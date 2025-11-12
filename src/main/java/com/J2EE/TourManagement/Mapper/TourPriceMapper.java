package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.Tour.TourUpdateDTO;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceCreateDTO;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceUpdateDTO;
import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourPriceMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tourDetail", ignore = true)
    })
    TourPrice toEntity(TourPriceCreateDTO tourPriceCreateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tourDetail", ignore = true)
    })
    void updateEntityFromDto(TourPriceUpdateDTO dto, @MappingTarget TourPrice entity);

    TourPriceDTO toDTO(TourPrice tourPrice);
    List<TourPriceDTO> toDTOList(List<TourPrice> tourPrice);
}
