package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.ReviewMapper;
import com.J2EE.TourManagement.Model.DTO.Review.ReviewCreateDTO;
import com.J2EE.TourManagement.Model.Review;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Repository.ReviewRepository;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final TourDetailRepository tourDetailRepository;

    //Create
    public Review handleSave(ReviewCreateDTO dto) throws InvalidException {
        TourDetail tourDetail = tourDetailRepository.findById(dto.getTourDetailId())
                .orElseThrow(() -> new InvalidException("Không tìm thấy TourDetail với id = " + dto.getTourDetailId()));

        Review review = reviewMapper.toEntity(dto);
        review.setTourDetail(tourDetail);

        return reviewRepository.save(review);
    }
}