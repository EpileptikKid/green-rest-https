package com.green.andrii.greenrest.controllers;

import com.green.andrii.greenrest.dto.ClientAdminDTO;
import com.green.andrii.greenrest.dto.ClientDTO;
import com.green.andrii.greenrest.dto.ClientProjDTO;
import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.services.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ModelMapper modelMapper;
    private final ClientService clientService;

    private final HttpHeaders headers = new HttpHeaders(new LinkedMultiValueMap<>()
                        {{add("Access-Control-Allow-Origin", "*");
                        add("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");}});

    @Autowired
    public ClientController(ClientService clientService, ModelMapper modelMapper) {
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ClientDTO>> getAllClients(@RequestParam(value = "date", defaultValue = "all") String date) {
        List<Client> result = clientService.getAllClientsByDate(convertDate(date));
        List<ClientDTO> responseClientList = result.stream().map(this::convertToClientDTO).toList();
        return  ResponseEntity.ok().headers(headers).body(responseClientList);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        System.out.println("great");
        HttpHeaders header = new HttpHeaders();
        header.add("Allow", "GET,POST,PUT,DELETE,OPTIONS");
        header.add("Access-Control-Allow-Headers", "Content-Type");
        return ResponseEntity.ok()
                .headers(header)
                .build();
    }

    @GetMapping("/name")
    public ResponseEntity<List<ClientProjDTO>> getAllClientsName(@RequestParam(value = "date", defaultValue = "today") String date) {
        List<Client> result = clientService.getAllClientsByDate(convertDate(date));
        List<ClientProjDTO> responseClientList = result.stream().map(this::convertToClientProjDTO).toList();
        return  ResponseEntity.ok().headers(headers).body(responseClientList);
    }

    @GetMapping("/admin")
    public  ResponseEntity<List<ClientAdminDTO>> getAllClientsForAdministrator(@RequestParam(value = "date", defaultValue = "today") String date) {
        return ResponseEntity.ok()
                .headers(headers)
                .body(convertClientAdminDTO(clientService.getAllClientsByDate(convertDate(date))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable("id") int id) {
        return ResponseEntity.ok().headers(headers).body(convertToClientDTO(clientService.getById(id)));
    }

    @GetMapping("/next")
    public ResponseEntity<ClientDTO> getNextClient(@RequestParam(value = "date", defaultValue = "today") String date) {
        Client client = clientService.getNext(convertDate(date));
        if (client != null) {
            return ResponseEntity.ok().headers(headers).body(convertToClientDTO(client));
        } else {
            return ResponseEntity.ok().headers(headers).body(null);
        }
    }

    private ClientDTO convertToClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    private ClientProjDTO convertToClientProjDTO(Client client) {
        return modelMapper.map(client, ClientProjDTO.class);
    }

    private List<ClientAdminDTO> convertClientAdminDTO(List<Client> clients) {
        return clients.stream().map(client -> modelMapper.map(client, ClientAdminDTO.class)).toList();
    }

    private Date convertDate(String strDate) {

        if (strDate.equals("tomorrow")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return calendar.getTime();
        }
        if (strDate.equals("after-tomorrow")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 2);
            return calendar.getTime();
        }
        return new Date();
    }
}
