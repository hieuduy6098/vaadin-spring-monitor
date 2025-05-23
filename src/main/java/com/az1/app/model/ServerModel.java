package com.az1.app.model;

import lombok.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value != null) {
                    map.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                // handle exception
                e.printStackTrace();
            }
        }

        return map;
    }
}
