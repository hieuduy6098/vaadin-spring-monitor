package com.az1.app.views.dashboard.domain;

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

        H2 title2 = new H2("Server Info");
        serverInfoTable = new GenericCustomTable<>();
        serverInfoTable.addTextColumn("ip",ServerInfoDashboardModel::getIp);
        serverInfoTable.addTextColumn("name",ServerInfoDashboardModel::getName);
        serverInfoTable.addTextColumn("cpu usage",item -> String.valueOf(item.getCpu()));
        serverInfoTable.addTextColumn("mem usage",item -> String.valueOf(item.getRam()));

        add(title1,hardwareInfoTable,title2,serverInfoTable);
    }

}
