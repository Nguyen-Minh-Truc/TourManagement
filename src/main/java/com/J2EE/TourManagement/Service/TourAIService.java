package com.J2EE.TourManagement.Service;

import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Repository.TourRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TourAIService {
    private final VectorStore vectorStore;
    private final TourRepository tourRepository;

    public TourAIService(VectorStore vectorStore, TourRepository tourRepository) {
        this.vectorStore = vectorStore;
        this.tourRepository = tourRepository;
    }

    public void addTourToVectorStore(Long tourId, String tourName, String description, Double price) {
        String content = "Tên tour: " + tourName + ". Mô tả: " + description + ". Giá: " + price;

        // 2. Tạo Metadata (để sau này tìm thấy vector thì biết nó là tour ID nào)
        Map<String, Object> metadata = Map.of(
                "tour_id", tourId,
                "price", price
        );

        // 3. Tạo Document và lưu vào store
        Document document = new Document(content, metadata);
        vectorStore.add(List.of(document));

        System.out.println("Đã thêm tour '" + tourName + "' vào Vector Store.");
    }

    @Transactional(readOnly = true) // Dùng readOnly để tối ưu hiệu năng query
    public String syncDatabaseToVectorStore() {
        List<Tour> tours = tourRepository.findAll(); // Hoặc findByStatus("ACTIVE") nếu muốn lọc

        if (tours.isEmpty()) {
            return "Database trống, không có gì để nạp!";
        }

        int count = 0;
        for (Tour tour : tours) {
            // 2. Xử lý dữ liệu trước khi nạp
            // Ưu tiên dùng Long Description, nếu null thì dùng Short Description
            String description = tour.getLongDesc();
            if (description == null || description.isEmpty()) {
                description = tour.getShortDesc();
            }

            // 3. Xử lý giá (Vì bảng Tour của bạn không có cột price, nó nằm ở TourDetail -> TourPrice)
            // Để đơn giản cho AI tìm kiếm, ta tạm để giá = 0 hoặc bạn cần viết query phức tạp hơn để lấy min_price.
            Double representativePrice = 0.0;

            // 4. Gọi hàm thêm vào Vector
            addTourToVectorStore(
                    tour.getId(),
                    tour.getTitle(),
                    description,
                    representativePrice
            );
            count++;
        }

        return "Đã đồng bộ thành công " + count + " tour vào bộ nhớ AI.";
    }

    public List<Document> searchTours(String query) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(query)              // Câu truy vấn của user
                .topK(3)                   // Lấy top 2 kết quả
                .similarityThreshold(0.5)  // (Tuỳ chọn) Độ chính xác tối thiểu 0.5
                .build();
        return vectorStore.similaritySearch(searchRequest);
    }
}
