package com.az1.app.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import com.az1.app.utils.TimeUtils;

/**
 * A service class that provides access to server-related system information.
 * This class uses the OSHI library to retrieve operating system and hardware details.
 */
@Service
public class ServerService {
    protected final SystemInfo systemInfo = new SystemInfo();
    protected final OperatingSystem os = new SystemInfo().getOperatingSystem();
    protected final HardwareAbstractionLayer hardware = new SystemInfo().getHardware();

    /**
     * Retrieves detailed operating system information as a formatted string.
     *
     * @return A string containing operating system details.
     */
    public String getOsInfoString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- Operating System Information ---\n");
        sb.append("Manufacturer: ").append(os.getManufacturer()).append("\n");
        sb.append("Family: ").append(os.getFamily()).append("\n");
        sb.append("Version: ").append(os.getVersionInfo().getVersion()).append("\n");
        sb.append("Build Number: ").append(os.getVersionInfo().getBuildNumber()).append("\n");
        sb.append("Code Name: ").append(os.getVersionInfo().getCodeName()).append("\n");
        sb.append("Operating System Architecture: ").append(os.getBitness()).append(" bit\n");
        sb.append("Process ID (PID): ").append(os.getProcessId()).append("\n");
        sb.append("System Uptime: ").append(TimeUtils.formatUptime(os.getSystemUptime())).append("\n");
        sb.append("System Boot Time: ").append(os.getSystemBootTime()).append("\n");
        sb.append("Number of Processes: ").append(os.getProcessCount()).append("\n");
        sb.append("Number of Threads: ").append(os.getThreadCount()).append("\n");
        return sb.toString();
    }

}
