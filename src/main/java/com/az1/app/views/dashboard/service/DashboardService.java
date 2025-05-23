package com.az1.app.views.dashboard.service;

import com.az1.app.model.NetworkModel;
import com.az1.app.model.ServerModel;
import com.az1.app.service.*;
import com.az1.app.views.dashboard.model.ServerInfoDashboardModel;
import com.vaadin.flow.component.UI;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class DashboardService {
    private final ServerService serverService = new ServerService();
    private final CpuService cpuService = new CpuService();
    private final MemService memService = new MemService();
    private final DiskService diskService = new DiskService();
    private final NetworkService networkService = new NetworkService();

    public Map<String, String> getOsInfo(){
        return serverService.getOsInfo().toMap();
    }

    public ServerInfoDashboardModel getServerInfo(){
        List<String> listIpV4 = networkService.getNetworkInfo().stream()
                .map(NetworkModel::getIPv4Addresses)
                .filter(ip -> ip != null && !ip.isEmpty())
                .toList();
        String ip;
        if (listIpV4.size() <= 1) {
            ip = listIpV4.getFirst();
        } else
            ip = String.join(",", listIpV4);

        float cpuUsage = cpuService.getCpuUsage();
        float memUsage = memService.getMemUsage();

        return new ServerInfoDashboardModel(ip,"mac",cpuUsage,memUsage);
    }



}
