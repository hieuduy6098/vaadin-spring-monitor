package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiskPartition {
    private String mountPoint;
    private String name;
    private String id;
    private String type;
    private String uuid;
    private String size;
}
