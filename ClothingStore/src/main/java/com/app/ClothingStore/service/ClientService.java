package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public String save(Client client) {
        clientRepository.save(client);
        return "Cliente " + client.getName() + " salvo com sucesso!";
    }

    public void saveAll(List<Client> clients){
        clientRepository.saveAll(clients);
    }

    public String update(Client client, Long id) {
        if (clientRepository.existsById(id)) {
            client.setId(id);
            clientRepository.save(client);
            return "Cliente " + client.getName() + " atualizado com sucesso!";
        } else {
            return "Cliente não encontrado!";
        }
    }

    public String delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return "Cliente com ID " + id + " deletado com sucesso!";
        } else {
            return "Cliente com ID " + id + " não encontrado!";
        }
    }

    public List<Client> findAll(){
        return clientRepository.findAll();
    }

    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    public List<Client> findByNameContaining(String name) {
        return clientRepository.findByNameContaining(name);
    }

    public List<Client> findByAgeGreaterThanEqual(Integer age){
        return clientRepository.findByAgeGreaterThanEqual(age);
    }

    public Optional<Client> findByCpf(String cpf){
        return clientRepository.findByCpf(cpf);
    }

    public List<Client> findByNameAndAge(String name, Integer age) {
        return clientRepository.findByNameAndAge(name, age);
    }
}
