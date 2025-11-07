package com.J2EE.TourManagement.Mapper;

import com.J2EE.TourManagement.Model.DTO.Review.*;
import com.J2EE.TourManagement.Model.Review;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewCreateDTO dto);
    ReviewResponseDTO toResponseDTO(Review review);
    List<ReviewResponseDTO> toResponseDTOList(List<Review> reviews);
}