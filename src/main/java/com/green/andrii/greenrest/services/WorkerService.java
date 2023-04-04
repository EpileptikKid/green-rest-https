package com.green.andrii.greenrest.services;

import com.green.andrii.greenrest.models.Worker;
import com.green.andrii.greenrest.repositories.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WorkerService {
    private final WorkerRepository workerRepository;

    @Autowired
    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    public void save(Worker worker) {
        workerRepository.save(worker);
    }

    public Worker getById(int id) {
        return workerRepository.getWorkerById(id);
    }

    public List<Worker> getAllWorkers() {
        return workerRepository.findAll();
    }

    public Worker getWorkerByEmail(String email) {
        return workerRepository.getWorkerByEmail(email);
    }

}
