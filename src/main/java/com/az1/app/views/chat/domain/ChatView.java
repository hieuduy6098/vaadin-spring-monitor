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
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@PageTitle("Chat")
@Route("chat")
@RouteAlias("")
@Menu(order = 1, icon = LineAwesomeIconUrl.COMMENTS)
public class ChatView extends VerticalLayout {
    private final ChatService chatService = new ChatService();
    private final BotService botService = new BotService();

    private MessageList list;
    private MessageInput input;
    private List<MessageListItem> items;

    private final UI ui;

    public ChatView() {
        this.ui = UI.getCurrent();
        addClassNames("chat-view", Width.FULL, Display.FLEX, Flex.AUTO);
        setSpacing(false);

        list = new MessageList();
        input = new MessageInput();
        list.setSizeFull();
        input.setWidthFull();

        Button clearButton = new Button("Delete chat", new Icon(VaadinIcon.TRASH));
        clearButton.addClickListener(e -> clearChat());

        add(clearButton, list, input);
        setSizeFull();
        expand(list);

        // Khôi phục tin nhắn từ session nếu có
        restoreMessages();

        input.addSubmitListener(submitEvent -> {
            handleUserInput(submitEvent.getValue());
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

    /*
    step 1: get input user > update chat
    step 2: get response AI from botService (BotService.java)
    step 3: get string text AI response from api (ApiHandleService.java)
    step 4: show AI response
     */
    private void handleUserInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return; // Bỏ qua tin nhắn trống
        }

        // Tạo tin nhắn người dùng
        MessageListItem newMessage = new MessageListItem(userInput, Instant.now(), "User", "./icons/user.png");
        newMessage.setUserColorIndex(1);

        items = new ArrayList<>(list.getItems());
        items.add(newMessage);
        list.setItems(items);
        chatService.saveMessages(items); // Lưu vào session
        scrollToBottom();

        // Tạo tin nhắn "Bot is typing..."
        MessageListItem loadingMessage = botService.getAiLoadingResponse();
        loadingMessage.setUserColorIndex(5);
        items.add(loadingMessage);
        list.setItems(items);
        chatService.saveMessages(items);
        scrollToBottom();

        // Xử lý phản hồi của bot trong một luồng riêng
        new Thread(() -> {
            /*
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // Lấy phản hồi từ bot
            MessageListItem botReply = botService.getAiResponse(newMessage);
            botReply.setUserColorIndex(5); // Màu khác cho bot

            // Cập nhật lại danh sách tin nhắn trên luồng UI
            ui.access(() -> {
                int loadingMessageIndex = items.indexOf(loadingMessage);
                if (loadingMessageIndex != -1) {
                    // Update the existing loading message with the bot's actual reply
                    items.set(loadingMessageIndex, botReply);
                } else {
                    // Fallback: If for some reason the loading message wasn't found, add the bot reply as a new message
                    items.add(botReply);
                }
                list.setItems(items);
                chatService.saveMessages(items);
                scrollToBottom();
            });
            */
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            StringBuilder currentBotText = new StringBuilder();
            String fullBotResponse = botService.getAiResponse(newMessage).getText(); // Get full response for now
            String[] words = fullBotResponse.split(" "); // Split into words

            for (String word : words) {
                currentBotText.append(word).append(" ");
                // Update the bot message on the UI thread
                ui.access(() -> {
                    loadingMessage.setText(currentBotText.toString().trim());
                    list.setItems(new ArrayList<>(items)); // Create a new list to trigger update
                    scrollToBottom();
                });
                try {
                    Thread.sleep(100); // Simulate typing delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore interrupt status
                    break;
                }
            }

            // After all words are displayed, save the final state if needed
            ui.access(() -> {
                chatService.saveMessages(items);
            });
        }).start();
    }

}