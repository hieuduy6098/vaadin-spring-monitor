package com.az1.app.service;

import com.az1.app.model.DiskModel;
import com.az1.app.model.DiskPartition;
import oshi.hardware.HWDiskStore;
import java.util.List;

import static com.az1.app.utils.ConvertSizeUtils.formatSize;

public class DiskService extends ServerService {
    public List<DiskModel> getDiskInfo() {
        List<HWDiskStore> diskStores = hardware.getDiskStores();

        return diskStores.stream()
                .map(ds -> DiskModel.builder()
                        .name(ds.getName())
                        .model(ds.getModel())
                        .serial(ds.getSerial())
                        .size(formatSize(ds.getSize()))
                        .reads(ds.getReads())
                        .writes(ds.getWrites())
                        .readBytes(formatSize(ds.getReadBytes()))
                        .writeBytes(formatSize(ds.getWriteBytes()))
                        .transferTime(String.format("%s %s",ds.getTransferTime(),"ms"))
                        .queueLength(ds.getCurrentQueueLength())
                        .timeStamp(ds.getTimeStamp())
                        .partitions(ds.getPartitions().stream()
                                .map(hw -> DiskPartition.builder()
                                        .mountPoint(hw.getMountPoint())
                                        .name(hw.getName())
                                        .id(hw.getUuid())
                                        .type(hw.getType())
                                        .uuid(hw.getUuid())
                                        .size(hw.getType())
                                        .build())
                                .toList()
                        )
                        .build())
                .toList();
    }
}
