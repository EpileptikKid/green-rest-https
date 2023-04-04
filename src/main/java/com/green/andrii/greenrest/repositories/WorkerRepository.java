package com.green.andrii.greenrest.repositories;

import com.green.andrii.greenrest.models.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Integer> {
    Worker getWorkerById(int id);

    Worker getWorkerByEmail(String email);

}
