package com.az1.app.service;

import com.az1.app.model.CpuModel;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CpuService extends ServerService {

    private final CentralProcessor processor = hardware.getProcessor();

    /**
     * Gets the average CPU usage over a given time interval as a percentage string.
     *
     * @param milliseconds The time interval (in milliseconds) to calculate CPU usage.
     * @return A string representing the average CPU usage (e.g., "15.32%").
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public String getCpuUsageString(long milliseconds) throws InterruptedException {
        double usage = getCpuUsagePercentage(milliseconds);
        return String.format("%.2f%%", usage);
    }

    /**
     * Gets the average CPU usage over a given time interval as a percentage value.
     *
     * @param milliseconds The time interval (in milliseconds) to calculate CPU usage.
     * @return The average CPU usage value (ranging from 0.0 to 100.0).
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public double getCpuUsagePercentage(long milliseconds) throws InterruptedException {
        long[] loadPrevious = processor.getSystemCpuLoadTicks();
        Thread.sleep(milliseconds);
        long[] loadNow = processor.getSystemCpuLoadTicks();
        return processor.getSystemCpuLoadBetweenTicks(loadPrevious) * 100;
    }

    /**
     * Gets the current average CPU usage as a percentage string.
     *
     * @return A string representing the current CPU usage (e.g., "10.50%").
     */
    public String getCurrentCpuUsageString() {
        double usage = getCurrentCpuUsagePercentage();
        return String.format("%.2f%%", usage);
    }

    /**
     * Gets the current average CPU usage as a percentage value.
     *
     * @return The current CPU usage value (ranging from 0.0 to 100.0).
     */
    public double getCurrentCpuUsagePercentage() {
        return processor.getSystemCpuLoad(1000) * 100;
    }

    /**
     * Gets the CPU usage for each individual core as a percentage string.
     *
     * @param milliseconds The time interval (in milliseconds) to calculate CPU usage.
     * @return A string representing CPU usage for each core (e.g., "[5.12%, 10.25%, 7.89%]").
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public String getIndividualCpuUsageString(long milliseconds) throws InterruptedException {
        double[] individualUsage = getIndividualCpuUsagePercentage(milliseconds);
        return Arrays.stream(individualUsage)
                .mapToObj(usage -> String.format("%.2f%%", usage))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Gets the CPU usage for each individual core as an array of percentage values.
     *
     * @param milliseconds The time interval (in milliseconds) to calculate CPU usage.
     * @return An array containing CPU usage values for each core (ranging from 0.0 to 100.0).
     * @throws InterruptedException if the thread is interrupted while sleeping.
     */
    public double[] getIndividualCpuUsagePercentage(long milliseconds) throws InterruptedException {
        long[][] prevTicks = processor.getProcessorCpuLoadTicks();
        Thread.sleep(milliseconds);
        return Arrays.stream(processor.getProcessorCpuLoadBetweenTicks(prevTicks))
                .map(usage -> usage * 100)
                .toArray();
    }

    /**
     * Gets the current CPU usage for each individual core as a percentage string.
     *
     * @return A string representing current CPU usage for each core (e.g., "[8.50%, 12.10%, 9.75%]").
     */
    public String getCurrentIndividualCpuUsageString() {
        double[] currentIndividualUsage = getCurrentIndividualCpuUsagePercentage();
        return Arrays.stream(currentIndividualUsage)
                .mapToObj(usage -> String.format("%.2f%%", usage))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Gets the current CPU usage for each individual core as an array of percentage values.
     *
     * @return An array containing current CPU usage values for each core (ranging from 0.0 to 100.0).
     */
    public double[] getCurrentIndividualCpuUsagePercentage() {
        return Arrays.stream(processor.getProcessorCpuLoad(1000))
                .map(usage -> usage * 100)
                .toArray();
    }

    /**
     * Gets detailed information about the CPU as a string.
     *
     * @return A string containing detailed CPU information.
     */
    public CpuModel getCpuInfo() {
        return CpuModel.builder()
                .processor(processor.getProcessorIdentifier().getName())
                .identifier(processor.getProcessorIdentifier().getIdentifier())
                .family(processor.getProcessorIdentifier().getFamily())
                .model(processor.getProcessorIdentifier().getModel())
                .stepping(processor.getProcessorIdentifier().getStepping())
                .microarchitecture(processor.getProcessorIdentifier().getMicroarchitecture())
                .vendor(processor.getProcessorIdentifier().getVendor())
                .vendorFrequency(processor.getProcessorIdentifier().getVendorFreq())
                .logicalProcessors(processor.getLogicalProcessorCount())
                .physicalPackages(processor.getPhysicalPackageCount())
                .physicalCores(processor.getPhysicalProcessorCount())
                .build();
    }
}
