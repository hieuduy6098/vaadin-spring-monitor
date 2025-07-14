package com.az1.app.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.util.List;

/**
 * The main view is a top-level placeholder for other views.
 */
@Layout
@AnonymousAllowed
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        // Tạo icon user
        Image userIcon = new Image("./icons/user.png", "User");
        userIcon.setWidth("36px");
        userIcon.setHeight("36px");
        userIcon.getStyle().set("margin-right", "16px");
        userIcon.getStyle().set("cursor", "pointer");

        // Tạo ContextMenu khi nhấn vào icon
        ContextMenu userMenu = new ContextMenu(userIcon);
        userMenu.setOpenOnClick(true); // mở menu khi click

        Icon settingsIcon = VaadinIcon.COG.create();
        settingsIcon.getStyle().set("font-size", "0.6rem");

        Span settingsLabel = new Span("Settings");
        settingsLabel.getStyle().set("font-size", "0.75rem");

        HorizontalLayout settingsItem = new HorizontalLayout(settingsIcon, settingsLabel);
        settingsItem.setAlignItems(FlexComponent.Alignment.START);
        settingsItem.setSpacing(true);
        userMenu.addItem(settingsItem, e -> Notification.show("Settings clicked"));

        Icon logoutIcon = VaadinIcon.SIGN_OUT.create();
        logoutIcon.getStyle().set("font-size", "0.6rem");

        Span logoutLabel = new Span("Log out");
        logoutLabel.getStyle().set("font-size", "0.75rem");

        HorizontalLayout logoutItem = new HorizontalLayout(logoutIcon, logoutLabel);
        logoutItem.setAlignItems(FlexComponent.Alignment.START);
        logoutItem.setSpacing(true);
        userMenu.addItem(logoutItem, e -> getUI().ifPresent(ui -> ui.navigate("login")));

        // Tạo layout cho tiêu đề và icon
        HorizontalLayout headerLayout = new HorizontalLayout(viewTitle, userIcon);
        headerLayout.setWidthFull();
        headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        headerLayout.setSpacing(true);
        headerLayout.expand(viewTitle); // đẩy title chiếm hết phần còn lại

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        // 1. Tạo logo từ file trong thư mục resources
        Image logo = new Image("icons/logo.jpg", "App Logo"); // Đảm bảo có file logo.png trong resources/public/images/
        logo.setWidth("100px"); // Kích thước logo
        logo.setHeight("100px");

        Span appName = new Span("");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);

        // 3. Đặt logo và tên vào một layout nhỏ và căn giữa
        HorizontalLayout titleLayout = new HorizontalLayout(logo, appName);
        titleLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        titleLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        titleLayout.setPadding(false);
        titleLayout.setSpacing(false);

        // 4. Tạo header với layout chứa logo + tên
        Header header = new Header(titleLayout);
        header.addClassNames(LumoUtility.Padding.MEDIUM);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        List<MenuEntry> menuEntries = MenuConfiguration.getMenuEntries();
        menuEntries.forEach(entry -> {
            if (entry.icon() != null) {
                nav.addItem(new SideNavItem(entry.title(), entry.path(), new SvgIcon(entry.icon())));
            } else {
                nav.addItem(new SideNavItem(entry.title(), entry.path()));
            }
        });

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        return MenuConfiguration.getPageHeader(getContent()).orElse("");
    }
}
