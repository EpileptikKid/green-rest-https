package com.green.andrii.greenrest.controllers;

import com.green.andrii.greenrest.dto.WorkerDTO;
import com.green.andrii.greenrest.models.Worker;
import com.green.andrii.greenrest.services.WorkerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/workers")
@CrossOrigin(origins = "http://localhost:3000/")
public class WorkerController {

    private final ModelMapper modelMapper;
    private final HttpHeaders headers = new HttpHeaders(new LinkedMultiValueMap<>()
    {{add("Access-Control-Allow-Origin", "http://localhost:3000/");}});
    private final WorkerService workerService;

    @Autowired
    public WorkerController(ModelMapper modelMapper, WorkerService workerService) {
        this.modelMapper = modelMapper;
        this.workerService = workerService;
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


    @GetMapping("")
    public ResponseEntity<List<Worker>> getAllWorkers() {
        return ResponseEntity.ok().headers(headers).body(workerService.getAllWorkers());
    }

    @PostMapping("")
    public ResponseEntity<String> newWorker(@RequestBody Worker worker) {
        workerService.save(worker);
        return ResponseEntity.ok().headers(headers).body("ok");
    }


    @PostMapping("/worker")
    public ResponseEntity<WorkerDTO> getWorkerByEmail(@RequestBody WorkerDTO email) {
        System.out.println(email);

        Worker returnedWorker = workerService.getWorkerByEmail(email.getEmail());
        System.out.println(returnedWorker);
        return ResponseEntity.ok().body(convertToWorkerDTO(returnedWorker));

    }

    private WorkerDTO convertToWorkerDTO(Worker worker) {
        return modelMapper.map(worker, WorkerDTO.class);
    }
}
//return ResponseEntity.ok().headers(headers).body(workerService.getWorkerByEmail(email.getEmail()));