package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Model.DTO.TourPrice.TourPriceDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourPriceMapper {
    TourPriceDTO toDTO(TourPrice tourPrice);
    List<TourPriceDTO> toDTOList(List<TourPrice> tourPrice);
}
