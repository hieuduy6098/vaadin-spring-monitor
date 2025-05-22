package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiskModel {
    private String name;
    private String model;
    private String serial;
    private String size;
    private long reads;
    private long writes;
    private String readBytes;
    private String writeBytes;
    private String transferTime;
    private long queueLength;
    private long timeStamp;
    private List<DiskPartition> partitions;
}
