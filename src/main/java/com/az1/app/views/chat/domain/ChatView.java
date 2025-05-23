package com.az1.app.views.chat.domain;

import com.az1.app.views.chat.service.BotService;
import com.az1.app.views.chat.service.ChatService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@PageTitle("Chat")
@Route("chat")
@Menu(order = 1, icon = LineAwesomeIconUrl.COMMENTS)
public class ChatView extends VerticalLayout {
    private final ChatService chatService = new ChatService();
    private final BotService botService = new BotService();

    private MessageList list;
    private MessageInput input;

    public ChatView() {
        addClassNames("chat-view", Width.FULL, Display.FLEX, Flex.AUTO);
        setSpacing(false);

        list = new MessageList();
        input = new MessageInput();

        list.setSizeFull();
        input.setWidthFull();

        // Nút Clear Chat
        Button clearButton = new Button("Delete chat", new Icon(VaadinIcon.TRASH));
        clearButton.addClickListener(e -> clearChat());

        add(clearButton, list, input);
        setSizeFull();
        expand(list);

        // Khôi phục tin nhắn từ session nếu có
        restoreMessages();

        input.addSubmitListener(submitEvent -> {
            try {
                handleUserInput(submitEvent.getValue());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void restoreMessages() {
        list.setItems(chatService.getMessages());
    }

    private void scrollToBottom() {
        UI.getCurrent().getPage().executeJs("document.querySelector('.chat-view vaadin-message-list').scrollToBottom()");
    }

    private void clearChat() {
        list.setItems(); // Xóa giao diện
        chatService.clearMessages(); // Xóa dữ liệu trong service/session
        Notification.show("Đoạn chat đã được xóa.");
    }

    private void handleUserInput(String userInput) throws IllegalAccessException {
        if (userInput == null || userInput.trim().isEmpty()) {
            return; // Bỏ qua tin nhắn trống
        }

        // Tạo tin nhắn người dùng
        MessageListItem newMessage = new MessageListItem(userInput, Instant.now(), "User");
        newMessage.setUserColorIndex(1);

        List<MessageListItem> items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);

        // Lưu vào session sau mỗi tin nhắn mới
        chatService.saveMessages(items);

        // Tạo phản hồi từ bot
        MessageListItem botReply = botService.getBotResponse(newMessage);
        items.add(botReply);

        list.setItems(items);
        scrollToBottom();

        // Lưu toàn bộ tin nhắn (người dùng + bot)
        chatService.saveMessages(items);

    }

}