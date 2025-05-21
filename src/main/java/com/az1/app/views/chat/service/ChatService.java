package com.az1.app.views.chat.service;

import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.server.VaadinSession;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ChatService {

    private static final String SESSION_KEY = "chatMessages";

    public List<MessageListItem> getMessages() {
        @SuppressWarnings("unchecked")
        List<MessageListItem> messages = (List<MessageListItem>) VaadinSession.getCurrent().getAttribute(SESSION_KEY);
        return messages != null ? new ArrayList<>(messages) : new ArrayList<>();
    }

    public void saveMessages(List<MessageListItem> messages) {
        VaadinSession.getCurrent().setAttribute(SESSION_KEY, new ArrayList<>(messages));
    }

    public void clearMessages() {
        VaadinSession.getCurrent().setAttribute(SESSION_KEY, new ArrayList<MessageListItem>());
    }

}
