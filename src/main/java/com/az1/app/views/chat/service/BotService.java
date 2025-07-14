package com.az1.app.views.chat.service;

import com.az1.app.model.*;
import com.az1.app.service.*;
import com.vaadin.flow.component.messages.MessageListItem;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BotService {
//    private final ApiHandleService apiHandleService = new ApiHandleService();
    @Autowired
    private ApiHandleService apiHandleService;

    public MessageListItem getAiResponse(MessageListItem userMessage){
        String userText = userMessage.getText().toLowerCase();
        String reply = apiHandleService.getResponseAi(userText);
        if(reply == null){
            reply = "empty response";
        }

        return new MessageListItem(reply, Instant.now(), "Chat Bot","./icons/logo.jpg");
    }

    public MessageListItem getAiLoadingResponse(){
        return new MessageListItem("bot is thinking", Instant.now(), "Chat Bot", "./icons/logo.jpg");
    }
}
