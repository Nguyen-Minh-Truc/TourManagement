package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Service.TourAIService;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai/tour")
public class TourAIController {
    private final TourAIService tourAIService;
    public TourAIController(TourAIService tourAIService) {
        this.tourAIService = tourAIService;
    }

    @PostMapping("/sync-database")
    public ResponseEntity<String> syncData() {
        String result = tourAIService.syncDatabaseToVectorStore();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/init-data")
    public String initData() {
        tourAIService.addTourToVectorStore(1L, "Du lịch Đà Lạt mộng mơ", "Khám phá thành phố ngàn hoa, không khí se lạnh, thích hợp cho các cặp đôi lãng mạn, có đồi thông và hồ Xuân Hương.", 2500000.0);
        tourAIService.addTourToVectorStore(2L, "Khám phá Hang Sơn Đoòng", "Thám hiểm hang động lớn nhất thế giới, trải nghiệm mạo hiểm, leo núi, lội suối, dành cho người thích cảm giác mạnh.", 60000000.0);
        tourAIService.addTourToVectorStore(3L, "Nghỉ dưỡng Phú Quốc", "Tắm biển xanh cát trắng, lặn ngắm san hô, nghỉ dưỡng resort 5 sao, phù hợp cho gia đình có trẻ nhỏ.", 5000000.0);

        tourAIService.addTourToVectorStore(4L, "Sapa mờ sương - Chinh phục Fansipan",
                "Trải nghiệm cái lạnh vùng cao, ngắm tuyết rơi (nếu may mắn), thăm bản Cát Cát của người H'mong, leo đỉnh Fansipan hùng vĩ, thưởng thức đồ nướng ban đêm.", 3500000.0);

        tourAIService.addTourToVectorStore(5L, "Hà Giang - Mùa hoa Tam Giác Mạch",
                "Cung đường đèo Mã Pí Lèng hiểm trở dành cho dân phượt, ngắm hoa tam giác mạch nở rộ, đi thuyền trên sông Nho Quế, văn hóa người dân tộc thiểu số.", 2800000.0);

        tourAIService.addTourToVectorStore(6L, "Vịnh Hạ Long - Du thuyền 5 sao",
                "Nghỉ đêm trên du thuyền sang trọng giữa kỳ quan thiên nhiên, chèo kayak, thăm hang Sửng Sốt, phù hợp cho tuần trăng mật hoặc nghỉ dưỡng cao cấp.", 8500000.0);

        tourAIService.addTourToVectorStore(7L, "Đà Nẵng - Hội An - Bà Nà Hills",
                "Thành phố đáng sống nhất Việt Nam, ngắm Cầu Rồng phun lửa, đi bộ trong phố cổ Hội An yên bình, thả đèn hoa đăng, khu vui chơi Fantasy Park sôi động.", 4200000.0);

        tourAIService.addTourToVectorStore(8L, "Cố đô Huế - Nét đẹp lịch sử",
                "Tham quan Đại Nội, lăng tẩm các vua Nguyễn, nghe ca trù trên sông Hương, thưởng thức ẩm thực cung đình, không gian trầm mặc, cổ kính.", 3000000.0);

        tourAIService.addTourToVectorStore(9L, "Nha Trang - Thiên đường biển đảo",
                "Thành phố biển sôi động, lặn biển ngắm san hô tại Hòn Mun, vui chơi tại VinWonders, tắm bùn khoáng, cuộc sống về đêm nhộn nhịp.", 3800000.0);

        tourAIService.addTourToVectorStore(10L, "Miền Tây sông nước - Chợ nổi Cái Răng",
                "Đi thuyền trên sông, thăm chợ nổi buôn bán tấp nập, vào vườn hái trái cây (chôm chôm, măng cụt), nghe đờn ca tài tử, người dân chất phác hiền lành.", 1900000.0);

        tourAIService.addTourToVectorStore(11L, "Côn Đảo - Về nguồn tâm linh",
                "Viếng mộ chị Võ Thị Sáu, tham quan nhà tù Côn Đảo, kết hợp tắm biển hoang sơ, không khí trang nghiêm và yên tĩnh, thích hợp cho người lớn tuổi.", 5500000.0);

        tourAIService.addTourToVectorStore(12L, "Thái Lan - Bangkok & Pattaya",
                "Thiên đường mua sắm giá rẻ, show chuyển giới Alcazar, chùa Vàng, ẩm thực đường phố cay nóng, massage Thái cổ truyền.", 6500000.0);

        tourAIService.addTourToVectorStore(13L, "Nhật Bản - Mùa hoa anh đào",
                "Ngắm hoa Sakura, núi Phú Sĩ, trải nghiệm tàu Shinkansen, ăn Sushi tươi sống, văn hóa kỷ luật, đường phố sạch sẽ hiện đại.", 35000000.0);

        return "Đã nạp 13 tour mẫu vào Vector Store!";
    }

    @GetMapping("/search")
    public List<String> search(@RequestParam("query") String query) {
        List<Document> results = tourAIService.searchTours(query);
        // Trả về kết quả dạng String dễ đọc
        return results.stream()
                .map(doc -> "Tìm thấy Tour ID: " + doc.getMetadata().get("tour_id") +
                        " - Nội dung khớp: " + doc.getText())
                .collect(Collectors.toList());
    }
}
