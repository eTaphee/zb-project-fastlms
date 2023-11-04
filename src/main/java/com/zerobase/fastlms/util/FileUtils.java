package com.zerobase.fastlms.util;

import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

public class FileUtils {
    public static String getNewSaveFile(String baseLocalPath, String originalFilename) {
        LocalDate now = LocalDate.now();

        String dir = String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        createDirectory(dir);

        String fileName = getRandomFileName(originalFilename);

        return String.format("%s%s", dir, fileName);
    }

    public static String getFileExtension(String fileName) {
        if (fileName != null) {
            int dotPos = fileName.lastIndexOf(".");
            if (dotPos > -1) {
                return fileName.substring(dotPos + 1);
            }
        }

        return null;
    }

    public static void createDirectory(String directory) {
        File file = new File(directory);
        if (!file.isDirectory()) {
            if (file.mkdirs()) {
                throw new RuntimeException("failed mkdirs");
            }
        }
    }

    public static String getRandomFileName(String fileName) {
        String fileExtension = getFileExtension(fileName);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return String.format("%s.%s", uuid, fileExtension);
    }
}
