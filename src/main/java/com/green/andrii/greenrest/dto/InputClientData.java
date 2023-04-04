package com.green.andrii.greenrest.dto;

import java.util.List;

public class InputClientData {
    private List<ProductDTO> products;
    private WorkerDTO worker;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public WorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(WorkerDTO worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        return "InputClientData{" +
                "products=" + products +
                ", worker=" + worker +
                '}';
    }
}
