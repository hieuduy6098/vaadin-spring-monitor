//package vn.viettelai.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.*;
//import vn.viettelai.data.BaseEntity;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@EqualsAndHashCode(callSuper = true)
//@Data
//@Entity
//@Table(name = "server")
//public class Server extends BaseEntity {
//    // === Server Info ===
//    @Column(name = "server_name", length = 100)
//    private String serverName;
//    @Column(name = "ip_address", length = 100)
//    private String ipAddress;
//    @Column(name = "server_type")
//    private String serverType; // e.g., "APP", "DB"
//    @Column(name = "server_status")
//    private String serverStatus; // e.g., "UP", "DOWN", "MAINTENANCE"
//    @Column(name = "description")
//    private String description; // Optional server state description
//    @Column(name = "operating_system")
//    private String operatingSystem;
//    @Column(name = "cpu")
//    private Double cpu; // Core
//    @Column(name = "memory")
//    private Double memory;  // GB
//    @Column(name = "disk")
//    private Double disk;    // GB
//    // === Application/Service Info ===
//    @Column(name = "app_name")
//    private String applicationName;
//    @Column(name = "app_version")
//    private String applicationVersion;
//    @Column(name = "app_status")
//    private String applicationStatus; // RUNNING, STOPPED, ERROR
//    @Column(name = "app_port")
//    private String applicationPort;
//    @Column(name = "app_description")
//    private String applicationDescription;
//
//}
