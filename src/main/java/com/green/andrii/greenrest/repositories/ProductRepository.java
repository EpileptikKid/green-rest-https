package com.green.andrii.greenrest.repositories;

import com.green.andrii.greenrest.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Modifying
    @Query("UPDATE Product e SET e.status = :newStatus WHERE e.id = :id")
    void updateStatusById(int id, String newStatus);
}
