package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ValidationService validationService;

    public String save(Client client) {
        clientRepository.save(client);
        return "Cliente " + client.getName() + " salvo com sucesso!";
    }

    public void saveAll(List<Client> clients) {
        clientRepository.saveAll(clients);
    }

    public String update(Client client, Long id) {
        validationService.validateClientById(id);
        client.setId(id);
        clientRepository.save(client);
        return "Cliente " + client.getName() + " atualizado com sucesso!";
    }

    public String delete(Long id) {
        validationService.validateClientById(id);
        clientRepository.deleteById(id);
        return "Cliente com ID " + id + " deletado com sucesso!";
    }

    public Client findById(Long id) {
        return validationService.validateClientById(id);
    }

    public List<Client> findAllClients() {
        List<Client> clients = clientRepository.findAll();
        validationService.validateList(clients, "cliente");
        return clients;
    }

    public List<Client> findByNameContaining(String name) {
        validationService.validateString(name, "name");
        List<Client> clients = clientRepository.findByNameContaining(name);
        validationService.validateList(clients, "cliente");
        return clients;
    }

    public List<Client> findByCpf(String cpf) {
        validationService.validateCpf(cpf);
        List<Client> client = clientRepository.findByCpf(cpf);
        validationService.validateList(client, "cliente");
        return client;
    }

    public List<Client> findByNameAndAge(String name, Integer age) {
        validationService.validateString(name, "name");
        validationService.validateAge(age);
        List<Client> clients = clientRepository.findByNameAndAge(name, age);
        validationService.validateList(clients, "cliente");
        return clients;
    }
}
