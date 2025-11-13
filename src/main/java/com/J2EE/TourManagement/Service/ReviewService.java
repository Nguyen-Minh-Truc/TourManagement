package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Mapper.ReviewMapper;
import com.J2EE.TourManagement.Model.DTO.Review.ReviewCreateDTO;
import com.J2EE.TourManagement.Model.DTO.Review.ReviewDTO;
import com.J2EE.TourManagement.Model.DTO.Review.ReviewUpdateDTO;
import com.J2EE.TourManagement.Model.Review;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Repository.ReviewRepository;
import com.J2EE.TourManagement.Repository.TourDetailRepository;
import com.J2EE.TourManagement.Util.error.InvalidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final TourDetailRepository tourDetailRepository;

    //Create
    public Review handleSave(ReviewCreateDTO dto)
            throws InvalidException {
        Review review = reviewMapper.toEntity(dto);

        // Gán tourDetail id cho review
        if (dto.getTourDetailId() != null) {
            TourDetail detail = tourDetailRepository.findById(dto.getTourDetailId())
                    .orElseThrow(() -> new InvalidException(
                            "Không tìm thấy TourDetail với id = " + dto.getTourDetailId()));
            review.setTourDetail(detail);
        }

        return reviewRepository.save(review);
    }

    //Read all by tourDetail id
    public List<Review> handleGetAll(Long id)
            throws InvalidException {

        Optional<TourDetail> opt = tourDetailRepository.findById(id);
        if (opt.isEmpty()) {
            throw new InvalidException(
                    "Không tìm thấy TourDetail Id (id = "+ id +")"
            );
        }

        return opt.get().getReviews();
    }

    //Update
    public Review handleUpdate(Long id, ReviewUpdateDTO dto)
            throws InvalidException {
        Review existing = reviewRepository.findById(id)
                .orElseThrow(() -> new InvalidException(
                        "Không tìm thấy Review để cập nhật (id = " + id + ")"));

        reviewMapper.updateEntityFromDto(dto, existing);

        // Gán tourDetail id cho review
        if (dto.getTourDetailId() != null) {
            TourDetail detail = tourDetailRepository.findById(dto.getTourDetailId())
                    .orElseThrow(() -> new InvalidException(
                            "Không tìm thấy TourDetail với id = " + dto.getTourDetailId()));
            existing.setTourDetail(detail);
        }

        return reviewRepository.save(existing);
    }
}