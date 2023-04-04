package com.green.andrii.greenrest.services;

import com.green.andrii.greenrest.models.Product;
import com.green.andrii.greenrest.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    public void saveAll(List<Product> products) {
        productRepository.saveAll(products);
    }

    public Product getById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateStatusById(int id, String newStatus) {
        productRepository.updateStatusById(id, newStatus);
    }
}
