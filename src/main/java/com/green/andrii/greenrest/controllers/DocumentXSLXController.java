package com.green.andrii.greenrest.controllers;

import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.services.ClientService;
import com.green.andrii.greenrest.utils.DocumentParser;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/document-xlsx")
public class DocumentXSLXController {

    private final ClientService clientService;
    @Qualifier("configuration1")
    private final DocumentParser documentParser;

    @Autowired
    public DocumentXSLXController(ClientService clientService,
                                  DocumentParser documentParser) {
        this.clientService = clientService;
        this.documentParser = documentParser;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadInvoiceList(@RequestParam("file")MultipartFile file, HttpServletResponse response) {
        List<Client> clients;
        try {
            clients = documentParser.parse(file);
        } catch (ParseException | IOException e) {
            return ResponseEntity.ofNullable("Сталася помилка");
        }
        int countAdded = clientService.saveAll(clients);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        return ResponseEntity.ok("Додано " + countAdded + " замовлень");
    }

}
