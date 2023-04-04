package com.green.andrii.greenrest.controllers;

import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.services.ClientService;
import com.green.andrii.greenrest.utils.ParserXSLX;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/document-xlsx")
public class DocumentXSLXController {

    private final ClientService clientService;

    @Autowired
    public DocumentXSLXController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadInvoiceList(@RequestParam("file")MultipartFile file, HttpServletResponse response) throws IOException, ParseException {

        FileInputStream inputFile = convertMPFileToFIS(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputFile);
        List<Client> clients = ParserXSLX.parse(workbook);
        int countAdded = clientService.saveAll(clients);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        if (countAdded < 5)
            return ResponseEntity.ok("Додано " + countAdded + " замовлення");
        else
            return ResponseEntity.ok("Додано " + countAdded + " замовлень");
    }

    private FileInputStream convertMPFileToFIS(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        File tempFile = File.createTempFile(file.getOriginalFilename(), null);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bytes);
        fos.close();
        FileInputStream fileInputStream = new FileInputStream(tempFile);
        tempFile.delete();
        return fileInputStream;
    }
}
