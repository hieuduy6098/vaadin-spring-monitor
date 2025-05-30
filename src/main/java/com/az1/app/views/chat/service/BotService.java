package com.az1.app.views.chat.service;

import com.az1.app.model.*;
import com.az1.app.service.*;
import com.vaadin.flow.component.messages.MessageListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class BotService {
    private final ApiHandleService apiHandleService = new ApiHandleService();

    // func call api get response from api ai
    public MessageListItem getAiResponse(MessageListItem userMessage){
        String userText = userMessage.getText().toLowerCase();
        String reply = apiHandleService.getResponseAi(userText);
        return new MessageListItem(reply, Instant.now(), "Chat Bot","./icons/logo.jpg");
    }

    public MessageListItem getAiLoadingResponse(){
        return new MessageListItem("Bot is typing", Instant.now(), "Chat Bot", "./icons/logo.jpg");
    }
}
