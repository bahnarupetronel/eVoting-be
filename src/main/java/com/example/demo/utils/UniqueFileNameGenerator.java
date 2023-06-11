package com.example.demo.utils;

import org.springframework.util.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UniqueFileNameGenerator {

    public static String generateUniqueFileName(String originalFileName) {
        // Get the file extension from the original file name
        String extension = StringUtils.getFilenameExtension(originalFileName);

        // Generate a unique string using UUID
        String uniqueString = UUID.randomUUID().toString();

        // Format the current date and time
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Combine the unique string, timestamp, and file extension to create the unique file name
        String uniqueFileName = timeStamp + "_" + uniqueString + "." + extension;

        return uniqueFileName;
    }
}