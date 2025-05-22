package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NetworkModel {
    private String deviceName;
    private String displayName;
    private long speed;
    private long mtu;
    private String MACAddress;
    private String IPv4Addresses;
    private String IPv6Addresses;
    private long packetsReceived;
    private long packetsSent;
    private long dataReceived;
    private long dataSent;
}
