package com.green.andrii.greenrest.services;

import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    @Transactional
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Transactional
    public int saveAll(List<Client> clients) {
        clientRepository.saveAll(clients);
        return clients.size();
    }

    @Transactional
    public void updateStatusById(int id, String newStatus) {
        clientRepository.updateStatusById(id, newStatus);
    }

    public Client getById(int id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

    public Client getNext(Date date) {
        return clientRepository.findFirstByDateAndStatus(date, "n");
    }

    public List<Client> getAllClientsByDate(Date date) {
        return clientRepository.findAllByDateOrderByName(date);
    }
}
