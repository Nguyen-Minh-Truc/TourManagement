package com.J2EE.TourManagement.Controller;

import com.J2EE.TourManagement.Model.Tour;
import com.J2EE.TourManagement.Model.TourDetail;
import com.J2EE.TourManagement.Model.TourPrice;
import com.J2EE.TourManagement.Service.TourChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TourChatController {

    private final TourChatService tourChatService;
    private final ChatClient.Builder chatClientBuilder;

    public TourChatController(TourChatService tourChatService, ChatClient.Builder chatClientBuilder) {
        this.tourChatService = tourChatService;
        this.chatClientBuilder = chatClientBuilder;
    }

    @GetMapping("/ai/suggest-tour")
    public String suggestTour(@RequestParam String question) {

        List<Tour> activeTours = tourChatService.getActiveToursForAI();

        String contextInformationTour = buildContextFromTours(activeTours);

        String systemMessage = """
            Bạn là một trợ lý tìm kiếm tour du lịch.
            Nhiệm vụ của bạn là xem "Câu hỏi của người dùng", sau đó tìm
            các tour phù hợp CHỈ CÓ TRONG "DANH SÁCH TOUR" được cung cấp.

            - Nếu TÌM THẤY tour khớp với địa điểm người dùng hỏi: 
              Hãy liệt kê chi tiết các tour đó.
            
            - Nếu KHÔNG TÌM THẤY tour nào khớp: 
              Hãy trả lời: "Xin lỗi, tôi không tìm thấy tour nào phù hợp."
            
            Chỉ được dùng thông tin từ "DANH SÁCH TOUR".
        """;

        String userPromptTemplate = """
            Đây là câu hỏi của người dùng: "{cauHoiNguoiDung}"
            
            Và đây là danh sách các tour hiện có trong hệ thống:
            --- DANH SÁCH TOUR ---
            {contextTour}
            --- KẾT THÚC DANH SÁCH ---
            
            Hãy dựa vào danh sách tour trên để đưa ra gợi ý cho người dùng.
        """;

        Map<String, Object> model = Map.of(
                "cauHoiNguoiDung", question,
                "contextTour", contextInformationTour
        );

        PromptTemplate template = new PromptTemplate(userPromptTemplate);
        String userMessageText = template.render(Map.of(
                "cauHoiNguoiDung", question,
                "contextTour", contextInformationTour
        ));

        // ===============================================
        // 2. SỬ DỤNG CHATCLIENT ĐỂ THỰC HIỆN YÊU CẦU
        // ===============================================

        ChatClient chatClient = chatClientBuilder.build();

        return chatClient.prompt()
                .system(systemMessage)
                .user(userMessageText)
                .call()
                .content();
    }

    @GetMapping(value = "/ai/debug-context", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> debugContext() {
        List<Tour> activeTours = tourChatService.getActiveToursForAI();

        String contextInformationTour = buildContextFromTours(activeTours);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return ResponseEntity.ok().headers(headers).body(contextInformationTour);
    }

    private String buildContextFromTours(List<Tour> tours) {
        if (tours.isEmpty()) {
            return "Hiện tại hệ thống không có tour nào đang hoạt động.";
        }

        StringBuilder contextBuilder = new StringBuilder();
        for (Tour tour : tours) {
            contextBuilder.append("===== TOUR =====\n");
            contextBuilder.append("- Tên Tour: ").append(tour.getTitle()).append("\n");
            contextBuilder.append("- Địa điểm: ").append(tour.getLocation()).append("\n");
            contextBuilder.append("- Thời lượng: ").append(tour.getDuration()).append("\n");
            contextBuilder.append("- Mô tả ngắn: ").append(tour.getShortDesc()).append("\n");
            contextBuilder.append("- Rating: ").append(tour.getRating()).append("/5.0\n");

            // Lấy các chi tiết (ngày khởi hành, giá)
            if (tour.getTourDetails() == null || tour.getTourDetails().isEmpty()) {
                contextBuilder.append("- Chi tiết & Giá: (Chưa cập nhật)\n");
            } else {
                contextBuilder.append("- Các tùy chọn (ngày khởi hành & giá):\n");
                for (TourDetail detail : tour.getTourDetails()) {
                    if (!"ACTIVE".equals(detail.getStatus())) continue; // Bỏ qua nếu detail không active

                    contextBuilder.append("  + Khởi hành: ").append(detail.getStartDay());
                    contextBuilder.append(" (từ '").append(detail.getStartLocation()).append("')\n");

                    // Lấy các mức giá (ADULT, CHILD, ...)
                    if (detail.getTourPrices() == null || detail.getTourPrices().isEmpty()) {
                        contextBuilder.append("    * Giá: (Chưa cập nhật)\n");
                    } else {
                        for (TourPrice price : detail.getTourPrices()) {
                            contextBuilder.append("    * ").append(price.getPriceType()).append(": ")
                                    .append(price.getPrice()).append(" VND\n");
                        }
                    }
                }
            }
            contextBuilder.append("===================\n\n");
        }
        return contextBuilder.toString();
    }
}
