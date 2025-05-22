package com.az1.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemModel {
    private String totalMemory;
    private String usedMemory;
    private String availableMemory;
}
