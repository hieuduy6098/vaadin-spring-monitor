package com.az1.app.service;

import com.az1.app.model.MemModel;
import org.springframework.stereotype.Service;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OSProcess;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.az1.app.utils.ConvertSizeUtils.formatSize;

public class MemService extends ServerService {
    // Reference to the system's global memory information
    private final GlobalMemory memory = hardware.getMemory();

    /**
     * Gets the memory usage information as a formatted string.
     *
     * @return A string containing total memory, used memory, available memory, and usage percentage.
     */
    public MemModel getMemoryInfo() {
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;
        double usagePercentage = (double) usedMemory / totalMemory * 100;

        return MemModel.builder()
                .totalMemory(formatSize(totalMemory))
                .usedMemory(formatSize(usedMemory))
                .availableMemory(formatSize(availableMemory))
                .build();
    }

    public float getMemUsage() {
        long totalMemory = memory.getTotal();
        long availableMemory = memory.getAvailable();
        long usedMemory = totalMemory - availableMemory;

        // Tính tỷ lệ phần trăm với phép chia số thực
        float usagePercentage = ((float) usedMemory / (float) totalMemory) * 100;

        // Làm tròn đến 2 chữ số thập phân

        return Math.round(usagePercentage * 100) / 100.0f;
    }

    /**
     * Gets a string representation of the top memory-consuming processes.
     *
     * @param limit The number of top memory-consuming processes to retrieve.
     * @return A formatted string listing the top processes by RAM usage.
     */
    public String getTopMemoryConsumingProcessesString(int limit) {
        List<OSProcess> processes = os.getProcesses(); // Retrieve all running processes

        // Sort processes by Resident Set Size (RSS) in descending order and limit the result
        List<OSProcess> sortedProcesses = processes.stream()
                .sorted(Comparator.comparingLong(OSProcess::getResidentSetSize).reversed())
                .limit(limit)
                .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("--- Top %d Processes by Memory Usage ---\n", limit));

        for (int i = 0; i < sortedProcesses.size(); i++) {
            OSProcess process = sortedProcesses.get(i);
            sb.append(String.format(
                    "%d. PID: %d, Name: %s, User: %s, Memory: %s (%.2f%%)\n",
                    i + 1,
                    process.getProcessID(),
                    process.getName(),
                    process.getUser(),
                    formatSize(process.getResidentSetSize()),
                    (double) process.getResidentSetSize() / systemInfo.getHardware().getMemory().getTotal() * 100
            ));
        }
        return sb.toString();
    }
}
