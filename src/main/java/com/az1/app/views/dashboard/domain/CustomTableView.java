package com.az1.app.views.dashboard.domain;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//@PageTitle("dashboard")
//@Route("dashboard")
//@Menu(order = 1, icon = LineAwesomeIconUrl.COMMENTS)
public class CustomTableView extends VerticalLayout {

    private final Div tableDiv;
    private int nextUserId = 4; // ID bắt đầu cho người dùng mới
    private final UI ui;

    public CustomTableView() {
        this.ui = UI.getCurrent();
        H2 title = new H2("Custom Table View");
        add(title);

        List<User> users = List.of(
                new User(1, "Nguyen Van A", "a@example.com"),
                new User(2, "Tran Thi B", "b@example.com"),
                new User(3, "Le Van C", "c@example.com")
        );

        this.tableDiv = createCustomTable(users);
        tableDiv.setWidthFull();
        add(tableDiv);

        startAutoUpdate();
    }

    private void startAutoUpdate() {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("start update table");
            // Tạo người dùng mới
            User newUser = new User(nextUserId++, "User " + nextUserId, "user" + nextUserId + "@example.com");

            // Cập nhật giao diện trong luồng UI
            System.out.println(ui.getUIId());
            ui.access(() -> {
                try {
                    System.out.println(ui.getUIId());
                    if (ui.isAttached()) {
                        tableDiv.add(
                                createCell(String.valueOf(newUser.getId())),
                                createCell(newUser.getName()),
                                createCell(newUser.getEmail())
                        );
//                        tableDiv.getElement().requestRepaint();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }, 5, 5, TimeUnit.SECONDS); // Bắt đầu sau 5s và cứ mỗi 5s lại chạy 1 lần
    }

    private Div createCustomTable(List<User> users) {
        Div container = new Div();
        container.getStyle()
                .set("display", "grid")
                .set("grid-template-columns", "repeat(3, 1fr)")
                .set("gap", "10px")
                .set("padding", "10px")
                .set("border", "1px solid #ccc");

        // Header
        container.add(createCell("ID"), createCell("Tên"), createCell("Email"));

        // Rows
        for (User user : users) {
            container.add(
                    createCell(String.valueOf(user.getId())),
                    createCell(user.getName()),
                    createCell(user.getEmail())
            );
        }

        return container;
    }

    private Component createCell(String text) {
        Div cell = new Div();
        cell.setText(text);
        cell.getStyle()
                .set("padding", "8px")
                .set("border-bottom", "1px solid #eee");
        return cell;
    }
}