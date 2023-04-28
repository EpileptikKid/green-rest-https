package com.green.andrii.greenrest.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ConverterFile {
    public static FileInputStream convertMPFileToFIS(MultipartFile file) {
        try {
            return new FileInputStream(File.createTempFile(file.getOriginalFilename(), null));
        } catch (IOException e) {
            return null;
        }
    }
}
