package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Model.DTO.Tour.TourPriceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourPriceMapper {
    TourPriceDTO toDTO(TourPrice tourPrice);
}
