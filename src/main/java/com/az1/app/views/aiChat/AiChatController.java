package com.az1.app.views.aiChat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.TextField;

public class AiChatController {

    private TextField messageField;
    private Div messageContainer;
    private Scroller scroller;

    public AiChatController(TextField messageField, Div messageContainer, Scroller scroller) {
        this.messageField = messageField;
        this.messageContainer = messageContainer;
        this.scroller = scroller;
    }

    public void sendMessage() {
        String text = messageField.getValue();
        if (!text.trim().isEmpty()) {
            Div message = new Div();
            message.setText(text);
            message.getStyle()
                    .set("background-color", "#dcf8c6")
                    .set("padding", "8px")
                    .set("border-radius", "5px")
                    .set("align-self", "flex-start")
                    .set("max-width", "90%");
            messageContainer.add(message);

            // Scroll xuống cuối mỗi khi có tin nhắn mới
            UI.getCurrent().access(() -> {
                scroller.scrollToBottom();
            });

            messageField.clear();
            messageField.focus();
        }
    }
}
