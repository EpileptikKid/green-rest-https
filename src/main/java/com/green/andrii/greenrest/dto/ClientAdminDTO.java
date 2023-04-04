package com.green.andrii.greenrest.dto;

import com.green.andrii.greenrest.models.Product;
import com.green.andrii.greenrest.models.Worker;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.List;

public class ClientAdminDTO {
    private String name;
    private int id;
    private Date date;
    private String manager;
    private String comment;
    private String status;
    private List<ProductDTO> products;

    private Date dateComplete;
    private int worker;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateComplete() {
        return dateComplete;
    }

    public void setDateComplete(Date dateComplete) {
        this.dateComplete = dateComplete;
    }

    public int getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        if (worker != null)
            this.worker = worker.getId();
    }
}
