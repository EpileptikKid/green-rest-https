package com.green.andrii.greenrest.repositories;

import com.green.andrii.greenrest.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllByDateOrderByName(Date date);

    Client findFirstByDateAndStatus(Date date, String status);

    @Modifying
    @Query("UPDATE Client c SET c.status = :newStatus WHERE c.id = :id")
    void updateStatusById(int id, String newStatus);

}
