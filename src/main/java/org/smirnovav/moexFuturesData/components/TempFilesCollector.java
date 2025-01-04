package org.smirnovav.moexFuturesData.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class TempFilesCollector {
    private List<File> tempFiles;

    public TempFilesCollector() {
        this.tempFiles = new ArrayList<>();
    }

    public void addNewTempFile(File file) {
        tempFiles.add(file);
    }


    @Scheduled(cron = "0 0 0 * * *")
    @Scheduled(cron = "0 0 6 * * *")
    @Scheduled(cron = "0 0 12 * * *")
    @Scheduled(cron = "0 0 18 * * *")
    public void deleteAllTempFiles() {
        if (!tempFiles.isEmpty()) {
            for (File file : tempFiles) {
                file.delete();
            }
            tempFiles = new ArrayList<>();
        }
    }
}
