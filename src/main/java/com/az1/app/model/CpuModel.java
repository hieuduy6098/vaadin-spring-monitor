package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CpuModel {
    private String processor;
    private String identifier;
    private String family;
    private String model;
    private String stepping;
    private String microarchitecture;
    private String vendor;
    private long vendorFrequency;
    private int logicalProcessors;
    private int physicalPackages;
    private int physicalCores;
}
