package com.green.andrii.greenrest.dto;

import com.green.andrii.greenrest.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientDTO {
    private String name;
    private int id;
    private String manager;
    private String comment;
    private String status;

    private List<ProductDTO> products;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getManager() { return manager; }
    public void setManager(String manager) { this.manager = manager; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<ProductDTO> getProducts() { return products; }

    public void setProducts(List<Product> productList) {
        this.products = productList.stream().map(product -> new ModelMapper().map(product, ProductDTO.class)).toList();
    }
}
