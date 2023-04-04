package com.green.andrii.greenrest.controllers;

import com.green.andrii.greenrest.dto.InputClientData;
import com.green.andrii.greenrest.dto.ProductDTO;
import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.models.Product;
import com.green.andrii.greenrest.models.Worker;
import com.green.andrii.greenrest.services.ClientService;
import com.green.andrii.greenrest.services.ProductService;
import com.green.andrii.greenrest.services.WorkerService;
import com.green.andrii.greenrest.utils.ClientStatusCalculator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:3000/")
public class ProductController {
    private final ProductService productService;
    private final ClientService clientService;
    private final WorkerService workerService;
    private final ClientStatusCalculator statusCalculator;
    private final ModelMapper modelMapper;

    private final HttpHeaders headers = new HttpHeaders(new LinkedMultiValueMap<>()
    {{add("Access-Control-Allow-Origin", "*");
        add("Access-Control-Allow-Headers", "Content-Type");
        add("Access-Control-Allow-Methods", "GET, POST");}});

    @Autowired
    public ProductController(ProductService productService, ClientService clientService, WorkerService workerService,
                             ClientStatusCalculator statusCalculator, ModelMapper modelMapper) {
        this.productService = productService;
        this.clientService = clientService;
        this.workerService = workerService;
        this.statusCalculator = statusCalculator;
        this.modelMapper = modelMapper;
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getClient(@PathVariable("id") int id) {
        System.out.println("ok");
        return ResponseEntity.ok().headers(headers).body(convertToProductDTO(productService.getById(id)));
    }



    @PostMapping("/{id_client}/{id_worker}")
    public ResponseEntity<String> saveProductsStatus(@PathVariable("id_client") int clientId,
                                                         @PathVariable("id_worker") int workerId,
                                                         @RequestBody List<ProductDTO> productDTOList) {
        for (ProductDTO productDTO : productDTOList) {
            productService.updateStatusById(productDTO.getId(), productDTO.getStatus());
        }
        clientService.updateStatusById(clientId, statusCalculator.calculate(productDTOList));
        Client client = clientService.getById(clientId);
        client.setStatus(statusCalculator.calculate(productDTOList));
        client.setDateComplete(new Date());
        Worker worker = workerService.getById(workerId);
        client.setWorker(worker);
        clientService.save(client);
        return ResponseEntity.ok().body("ok");
    }

    @PostMapping("/{id_client}")
    public ResponseEntity<String> saveProductsStatus(@PathVariable("id_client") int clientId,
                                                     @RequestBody InputClientData inputData) {
        List<ProductDTO> productDTOList = inputData.getProducts();
        for (ProductDTO productDTO : productDTOList) {
            productService.updateStatusById(productDTO.getId(), productDTO.getStatus());
        }
        Client client = clientService.getById(clientId);
        String statusClient = statusCalculator.calculate((productDTOList));
        client.setStatus(statusClient);
        client.setDateComplete(new Date());
        Worker worker = workerService.getWorkerByEmail(inputData.getWorker().getEmail());
        client.setWorker(worker);
        clientService.save(client);
        return ResponseEntity.ok().body(statusClient);
    }

    private ProductDTO convertToProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
