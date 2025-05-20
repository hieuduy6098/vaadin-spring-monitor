package com.az1.app.views.chat;

import com.vaadin.collaborationengine.CollaborationMessageInput;
import com.vaadin.collaborationengine.CollaborationMessageList;
import com.vaadin.collaborationengine.UserInfo;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PageTitle("Chat")
@Route("chat")
@Menu(order = 1, icon = LineAwesomeIconUrl.COMMENTS)
public class ChatView extends VerticalLayout {
    private MessageList list;
    private MessageInput input;

    public ChatView() {
        addClassNames("chat-view", Width.FULL, Display.FLEX, Flex.AUTO);
        setSpacing(false);

        list = new MessageList();
        input = new MessageInput();

        list.setSizeFull();
        input.setWidthFull();

        // Khôi phục tin nhắn từ session nếu có
        restoreMessages();

        input.addSubmitListener(submitEvent -> {
            MessageListItem newMessage = new MessageListItem(
                    submitEvent.getValue(), Instant.now(), "Milla Sting");

            List<MessageListItem> items = new ArrayList<>(list.getItems());
            items.add(newMessage);
            list.setItems(items);

            // Lưu vào session sau mỗi tin nhắn mới
            saveMessages(items);
        });

        add(list, input);
        setSizeFull();
        expand(list);
    }


    private void restoreMessages() {
        VaadinSession session = VaadinSession.getCurrent();
        @SuppressWarnings("unchecked")
        List<MessageListItem> savedMessages = (List<MessageListItem>) session.getAttribute("chatMessages");

        if (savedMessages != null && !savedMessages.isEmpty()) {
            list.setItems(savedMessages);
        }
    }

    private void saveMessages(List<MessageListItem> messages) {
        VaadinSession.getCurrent().setAttribute("chatMessages", new ArrayList<>(messages));
    }
}