package com.az1.app.views.chat.domain;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;

public class CustomMessageInput extends VerticalLayout {

    private TextArea messageField;
    private Button sendButton;
    private MessageSendListener sendListener;

    public interface MessageSendListener {
        void onMessageSent(String message);
    }

    public CustomMessageInput() {
        initializeComponents();
        setupLayout();
        setupStyling();
        setupEventHandlers();
    }

    private void initializeComponents() {
        messageField = new TextArea();
        messageField.setPlaceholder("Đặt câu hỏi tại đây...");
        messageField.setMinHeight("60px");
        messageField.setMaxHeight("200px");
        messageField.getStyle()
                .set("overflow", "hidden");

        // Tạo icon arrow up trong circle
        Icon arrowIcon = VaadinIcon.ARROW_UP.create();
        sendButton = new Button(arrowIcon);
        sendButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    }

    private void setupLayout() {
        VerticalLayout inputLayout = new VerticalLayout();
        inputLayout.setWidthFull();
        inputLayout.setSpacing(false);
        inputLayout.setPadding(false);
        inputLayout.setAlignItems(Alignment.STRETCH);

        // TextArea chiếm toàn bộ không gian còn lại
//        messageField.setWidthFull();
        inputLayout.add(messageField);

        // 2. Nút send
        sendButton.getStyle()
                .set("align-self", "flex-end"); // căn nút về phải

        inputLayout.add(sendButton);
        add(inputLayout);
    }

    private void setupStyling() {
        // Styling cho container
        addClassName("custom-message-input");
        getStyle()
                .set("background", "#f8f9fa")
                .set("border-radius", "24px")
                .set("padding", "8px 12px")
                .set("box-shadow", "0 2px 8px rgba(0,0,0,0.1)")
                .set("border", "1px solid #e1e5e9");

        // Styling cho TextArea
        messageField.addClassName("message-input-field");
        messageField.getStyle()
                .set("background", "transparent")
                .set("border", "none")
                .set("box-shadow", "none")
                .set("--vaadin-focus-ring-width", "none")
                .set("padding", "0");

        // Remove default TextArea styling
        messageField.getElement().getStyle()
                .set("--lumo-border-radius", "0")
                .set("--lumo-contrast-10pct", "transparent");

        // Styling cho Send Button
        sendButton.addClassName("send-button");
        sendButton.getStyle()
                .set("border-radius", "50%")
                .set("min-width", "40px")
                .set("height", "40px")
                .set("margin-left", "8px")
                .set("background", "#ff1744")
                .set("color", "white")
                .set("border", "none")
                .set("cursor", "pointer");

        // Hover effect cho button
        sendButton.getElement().addEventListener("mouseenter", e -> {
            sendButton.getStyle().set("background", "#d50000");
        });

        sendButton.getElement().addEventListener("mouseleave", e -> {
            sendButton.getStyle().set("background", "#ff1744");
        });
    }

    private void setupEventHandlers() {
        // Xử lý click button
        sendButton.addClickListener(e -> sendMessage());

        // Sử dụng JavaScript để handle preventDefault
        messageField.getElement().executeJs(
                "this.addEventListener('keydown', function(e) {" +
                        "    if (e.key === 'Enter' && !e.shiftKey) {" +
                        "        e.preventDefault();" +
                        "        this.blur();" +
                        "        this.dispatchEvent(new Event('send-message'));" +
                        "    }" +
                        "});"
        );
//
        // Listen for custom event
        messageField.getElement().addEventListener("send-message", e -> {
            sendMessage();
        });

        messageField.focus();
    }

    private void sendMessage() {
        String message = messageField.getValue().trim();
        if (!message.isEmpty() && sendListener != null) {
            sendListener.onMessageSent(message);
            messageField.clear();
            messageField.focus();
        }
    }

    // Getters và Setters
    public void setMessageSendListener(MessageSendListener listener) {
        this.sendListener = listener;
    }

    public String getValue() {
        return messageField.getValue();
    }

    public void setValue(String value) {
        messageField.setValue(value);
    }

    public void setPlaceholder(String placeholder) {
        messageField.setPlaceholder(placeholder);
    }

    public void setEnabled(boolean enabled) {
        messageField.setEnabled(enabled);
        sendButton.setEnabled(enabled);
    }

    public void focus() {
        messageField.focus();
    }
}