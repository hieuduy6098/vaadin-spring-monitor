package com.az1.app.service;

import org.springframework.stereotype.Service;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import java.util.List;
import static com.az1.app.utils.ConvertSizeUtils.formatSize;

@Service
public class DiskService extends ServerService {
    /**
     * Retrieves detailed information about all disk stores (physical disks) and their respective partitions,
     * formatted as a human-readable string.
     *
     * The returned string includes:
     * - General disk information (name, model, serial number, size)
     * - I/O statistics (reads, writes, bytes read/written, transfer time)
     * - Partition details for each disk (mount point, name, UUID, type, size)
     *
     * @return A formatted string containing detailed disk and partition information.
     */
    public String getDetailedDiskInfoString() {
        List<HWDiskStore> diskStores = hardware.getDiskStores();
        StringBuilder sb = new StringBuilder();
        sb.append("--- Detailed Disk Information ---\n");

        for (HWDiskStore disk : diskStores) {
            sb.append("------------------------------------\n");
            sb.append("Name: ").append(disk.getName()).append("\n");
            sb.append("Model: ").append(disk.getModel()).append("\n");
            sb.append("Serial: ").append(disk.getSerial()).append("\n");
            sb.append("Size: ").append(formatSize(disk.getSize())).append("\n");
            sb.append("Reads: ").append(disk.getReads()).append("\n");
            sb.append("Writes: ").append(disk.getWrites()).append("\n");
            sb.append("Read Bytes: ").append(formatSize(disk.getReadBytes())).append("\n");
            sb.append("Write Bytes: ").append(formatSize(disk.getWriteBytes())).append("\n");
            sb.append("Transfer Time: ").append(disk.getTransferTime()).append(" ms\n");
            sb.append("Queue Length: ").append(disk.getCurrentQueueLength()).append("\n");
            sb.append("Time Stamp: ").append(disk.getTimeStamp()).append("\n");

            List<HWPartition> partitions = disk.getPartitions();
            if (!partitions.isEmpty()) {
                sb.append("\n  Partitions:\n");
                for (HWPartition partition : partitions) {
                    sb.append("    ------------------------\n");
                    sb.append("    Mount Point: ").append(partition.getMountPoint()).append("\n");
                    sb.append("    Name: ").append(partition.getName()).append("\n");
                    sb.append("    ID: ").append(partition.getUuid()).append("\n");
                    sb.append("    Type: ").append(partition.getType()).append("\n");
                    sb.append("    UUID: ").append(partition.getUuid()).append("\n");
                    sb.append("    Size: ").append(formatSize(partition.getSize())).append("\n");
                }
            } else {
                sb.append("\n  No partitions found.\n");
            }
        }
        sb.append("------------------------------------\n");
        return sb.toString();
    }
}
