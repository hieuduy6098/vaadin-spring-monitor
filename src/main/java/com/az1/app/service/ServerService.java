package com.az1.app.service;

import com.az1.app.model.ServerModel;
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
     * Retrieves detailed operating system information as a ServerModel object.
     *
     * @return ServerModel containing OS details such as manufacturer, version, uptime, and process/thread counts.
     */
    public ServerModel getOsInfoString() {
        return ServerModel.builder()
                .manufacturer(os.getManufacturer())
                .family(os.getFamily())
                .version(os.getVersionInfo().getVersion())
                .buildNumber(os.getVersionInfo().getBuildNumber())
                .codeName(os.getVersionInfo().getCodeName())
                .operatingSystemArchitecture(os.getBitness())
                .pid(os.getProcessId())
                .systemUptime(TimeUtils.formatUptime(os.getSystemUptime()))
                .systemBootTime(os.getSystemBootTime())
                .numberOfProcesses(os.getProcessCount())
                .numberOfThreads(os.getThreadCount())
                .build();
    }
}
