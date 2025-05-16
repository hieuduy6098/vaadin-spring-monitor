package com.az1.app.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServerModel {
    private String manufacturer;
    private String family;
    private String version;
    private String buildNumber;
    private String codeName;
    private int operatingSystemArchitecture;
    private int pid;
    private String systemUptime;
    private long systemBootTime;
    private int numberOfProcesses;
    private int numberOfThreads;
}
