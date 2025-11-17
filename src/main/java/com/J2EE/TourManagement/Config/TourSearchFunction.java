//package com.J2EE.TourManagement.Config;
//
//import com.J2EE.TourManagement.Model.DTO.TourSearchFilter;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Description;
//
//import java.util.function.Function;
//
//@Configuration
//public class TourSearchFunction {
//
//    private final ChatClient chatClient;
//
//    public TourSearchFunction(ChatClient chatClient) {
//        this.chatClient = chatClient;
//    }
//
//    @Bean
//    @Description("Phân tích query để trích xuất semanticQuery, maxPrice, requiredTags. Trả về JSON.")
//    public Function<TourSearchFilterRequest, TourSearchFilter> tourSearchFilterFunction() {
//        return request -> {
//            String prompt = """
//                Phân tích câu hỏi sau, trả về đúng JSON (không giải thích):
//                "%s"
//
//                {
//                  "semanticQuery": "phần tìm kiếm chính",
//                  "maxPrice": số tiền tối đa (0 nếu không có),
//                  "requiredTags": ["tag1", "tag2"]
//                }
//                """.formatted(request.query());
//
//            return chatClient.prompt()
//                    .user(prompt)
//                    .call()
//                    .entity(TourSearchFilter.class);
//        };
//    }
//}
//
//record TourSearchFilterRequest(String query) {}
