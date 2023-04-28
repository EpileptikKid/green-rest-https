package com.green.andrii.greenrest.utils;

import com.green.andrii.greenrest.models.Client;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface DocumentParser {
    List<Client> parse(MultipartFile file) throws ParseException, IOException;
}
