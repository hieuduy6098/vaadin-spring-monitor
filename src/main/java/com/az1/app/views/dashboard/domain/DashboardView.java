package com.az1.app.views.dashboard.domain;

import com.az1.app.model.ServerModel;
import com.az1.app.views.chat.service.ChatService;
import com.az1.app.views.dashboard.model.ServerInfoDashboardModel;
import com.az1.app.views.dashboard.service.DashboardService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PageTitle("Dashboard")
@Route("dashboard")
@Menu(order = 0, icon = LineAwesomeIconUrl.CHART_AREA_SOLID)
public class DashboardView extends VerticalLayout {
    private final DashboardService dashboardService = new DashboardService();
    private GenericCustomTable<ServerInfoDashboardModel> serverInfoTable;
    private final UI ui;

    public DashboardView() {
        this.ui = UI.getCurrent();

        H2 title1 = new H2("Hardware Info");
        GenericCustomTable<Map.Entry<String,String>> hardwareInfoTable = new GenericCustomTable<>();
        hardwareInfoTable.addTextColumn("Name",Map.Entry::getKey);
        hardwareInfoTable.addTextColumn("Value",Map.Entry::getValue);
        hardwareInfoTable.setItems(dashboardService.getOsInfo().entrySet());


        H2 title2 = new H2("Server Info");
        serverInfoTable = new GenericCustomTable<>();
        serverInfoTable.addTextColumn("ip",ServerInfoDashboardModel::getIp);
        serverInfoTable.addTextColumn("name",ServerInfoDashboardModel::getName);
        serverInfoTable.addTextColumn("cpu usage",item -> String.valueOf(item.getCpu()));
        serverInfoTable.addTextColumn("mem usage",item -> String.valueOf(item.getRam()));
        ServerInfoDashboardModel data = dashboardService.getServerInfo();
        serverInfoTable.setItems(data);

        add(title1,hardwareInfoTable,title2,serverInfoTable);

        startAutoUpdate();
    }

    public void startAutoUpdate(){
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            ui.access(() -> {
                try {
                    System.out.println(ui.getUIId());
                    if (ui.isAttached()) {
                        ServerInfoDashboardModel data = dashboardService.getServerInfo();
                        serverInfoTable.setItems(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }, 5, 5, TimeUnit.SECONDS);
    }
}
