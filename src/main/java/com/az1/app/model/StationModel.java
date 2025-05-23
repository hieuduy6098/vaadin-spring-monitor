package com.az1.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationModel {
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private String createdBy;
    private String updatedBy;
    private String code;
    private String name;
    private Double longitude;
    private Double latitude;
    private Object address;
    private Object note;
    private Object dataLogger;
    private String method;
    private String attachFileLicense;
    private String attachFileConnect;
    private Date operationDate;
    private Integer frequency;
    private Object nreType;
    private Integer status;
    private Object agency;
    private Object area;
    private Object agent;
    private Object province;
    private Object riverBasin;
    private Object district;
    private Object commune;
    private Object unit;
    private Object value;
    private Boolean isPeriodic;
    private Boolean isAutomatic;
    private Integer activate;
    private Object nmStationType;
    private Boolean audioAlert;
    private Boolean smsActive;
}
