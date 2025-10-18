package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.DTO.Tour.TourDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TourDetailMapper.class})
public interface TourMapper {
    TourDTO toDTO(Tour tour);
    List<TourDTO> toDTOList(List<Tour> tours);
}
