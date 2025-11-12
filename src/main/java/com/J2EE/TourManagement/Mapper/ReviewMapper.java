package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.Review.*;
import com.J2EE.TourManagement.Model.DTO.TourDetail.TourDetailUpdateDTO;
import com.J2EE.TourManagement.Model.Review;
import com.J2EE.TourManagement.Model.TourDetail;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tourDetail", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(ReviewCreateDTO dto);

    ReviewDTO toResponseDTO(Review review);

    List<ReviewDTO> toResponseDTOList(List<Review> reviews);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tourDetail", ignore = true)
    @Mapping(target = "reviewerName", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ReviewUpdateDTO dto, @MappingTarget Review entity);
}