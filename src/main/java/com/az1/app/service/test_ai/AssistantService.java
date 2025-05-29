//package vn.viettelai.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.ai.chat.client.ChatClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import vn.viettelai.data.BaseService;
//import vn.viettelai.model.CreateAssistantRequest;
//import vn.viettelai.model.ai.ChatResponse;
//import vn.viettelai.tools.NetworkTools;
//import vn.viettelai.tools.ServerTools;
//import vn.viettelai.tools.TelegramTools;
//
//
//@Service
//@Log4j2
//public class AssistantService extends BaseService {
//
//    private final ChatClient chatClient;
//
//    @Autowired
//    public AssistantService(ChatClient chatClient) {
//        this.chatClient = chatClient;
//
//    }
//
//    public String getSystemHealth() {
//        return "OK!";
//    }
//
//    public String getSystemConfig() {
//        return "OK!";
//    }
//
//    public String processAlert(CreateAssistantRequest request) {
//        return "OK!";
//    }
//
//    public String processSystemQuery(CreateAssistantRequest request) {
//        log.info("Chatting with generic options: {}", request.getMessage());
//
//        return this.chatClient.prompt()
//                .user(request.getMessage())
//                .call()
//                .content();
//    }
//
//}