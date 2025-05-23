package com.az1.app.views.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerInfoDashboardModel {
    private String ip;
    private String name;
    private float cpu;
    private float ram;
}
