package com.J2EE.TourManagement.Service; // Tạo thư mục Service nếu chưa có

import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // QUAN TRỌNG

import java.util.List;

@Service
public class TourChatService {

    private final TourRepository tourRepository;

    public TourChatService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    // Đánh dấu @Transactional để giữ session mở
    @Transactional(readOnly = true)
    public List<Tour> getActiveToursForAI() {
        List<Tour> tours = tourRepository.findActiveToursWithDetails();

        if (!tours.isEmpty()) {
            tourRepository.fetchPricesForTourDetails(tours);
        }

        return tours;
    }
}