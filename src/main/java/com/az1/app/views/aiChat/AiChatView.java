package com.az1.app.views.aiChat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("az1 ai agent chat")
@Route("")
public class AiChatView extends VerticalLayout {

    private Div messageContainer = new Div();
    private TextField messageField = new TextField();
    private Button sendButton = new Button("Gửi", VaadinIcon.PAPERPLANE.create());
    private Scroller scroller; // Khai báo Scroller ở cấp class

    private AiChatController chatController;

    public AiChatView() {
        // Thiết lập vùng hiển thị tin nhắn với Scroller
        scroller = new Scroller(messageContainer);
        scroller.setScrollDirection(Scroller.ScrollDirection.VERTICAL);
        scroller.setSizeFull();

        // Thiết lập thanh nhập liệu và nút gửi
        HorizontalLayout inputLayout = new HorizontalLayout(messageField, sendButton);
        inputLayout.setWidth("100%");
        messageField.setWidth("100%");



        chatController = new AiChatController(messageField, messageContainer, scroller);
        // Hành động khi nhấn nút gửi
        sendButton.addClickListener(event -> chatController.sendMessage());

        // Cấu hình layout chính
        setSizeFull();
        setSpacing(false);
        add(scroller, inputLayout);
        scroller.setHeight("80%");
        inputLayout.setWidth("100%");

        // Focus vào ô nhập liệu
        messageField.focus();

        // Thêm CSS để định dạng tin nhắn
        messageContainer.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("padding", "10px")
                .set("gap", "10px");
    }
}
