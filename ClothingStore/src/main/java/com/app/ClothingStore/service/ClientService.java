package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (clientRepository.existsById(id)) {
            client.setId(id);
            clientRepository.save(client);
            return "Cliente " + client.getName() + " atualizado com sucesso!";
        } else {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado!"); //msg de erro
        }
    }

    public String delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return "Cliente com ID " + id + " deletado com sucesso!";
        } else {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado!");
        }
    }

    public List<Client> findAll(){
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw  new EntityNotFoundException("Nenhum cliente encontrado!");
        }
        return clients;
    }

    public Client findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID não deve ser nulo ou negativo!");
        }
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com o ID " + id + " não encontrado!"));
    }

    public List<Client> findByNameContaining(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'name' é obrigatório e não pode estar vazio!");
        }
        if (name.matches("\\d+")) { // 'matches' verifica se a string corresponde à expressão regular fornecida
            throw new IllegalArgumentException("O parâmetro 'name' não pode ser um número!");
        }
        List<Client> clients = clientRepository.findByNameContaining(name);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Nenhum cliente encontrado com a letra/palavra: " +name);
        }
        return clients;
    }

    public List<Client> findByCpf(String cpf){
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'cpf' é obrigatório e não pode estar vazio!");
        }
        //validando o cpf no request
        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        if (!cpf.matches(cpfRegex)) {
            throw new IllegalArgumentException("O CPF fornecido não está no formato correto!");
        }
        List<Client> client = clientRepository.findByCpf(cpf);
        if (client.isEmpty()) {
            throw new EntityNotFoundException("Nenhum cliente encontrado com o CPF: " +cpf);
        }
        return client;
    }

    public List<Client> findByNameAndAge(String name, Integer age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("O parâmetro 'name' é obrigatório e não pode estar vazio!");
        }
        if (age!= null && age <= 0) {
            throw new IllegalArgumentException("A idade deve ser um valor positivo!");
        }
        List<Client> clients = clientRepository.findByNameAndAge(name, age);
        if (clients.isEmpty()) {
            throw new EntityNotFoundException("Nenhum cliente encontrado com o nome " + name + (age != null ? " e idade " + age : "") + ".");
        }
        return clients;
    }

}
