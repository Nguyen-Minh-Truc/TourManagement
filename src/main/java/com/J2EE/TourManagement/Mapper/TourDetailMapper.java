package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TourPriceMapper.class})
public interface TourDetailMapper {
    TourDetailDTO toDTO(TourDetail tourDetail);
    List<TourDetailDTO> toDTOList(List<TourDetail> tourDetails);
}