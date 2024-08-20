package com.app.ClothingStore.controller;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Client client) {
        try {
            String mensagem = clientService.save(client);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody List<Client> clients){
        try {
            clientService.saveAll(clients);
            return new ResponseEntity<>("Clientes salvos com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Client client, BindingResult result) { //BindingResult armazena o resultado, caso errado trata corretamente e cai no if
        if (result.hasErrors()) {
            String errorMessage = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        try {
            String mensagem = clientService.update(client, id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String mensagem = clientService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() { //com o <?> posso retornar qualquer tipo de dado :)
        try {
            List<Client> clients = clientService.findAll();
            if (clients.isEmpty()) {
                return new ResponseEntity<>("Nenhum cliente encontrado!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>("O ID não deve ser negativo ou nulo.", HttpStatus.BAD_REQUEST);
        }
        Optional<Client> client = clientService.findById(id);
        if (client.isPresent()) {
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("ID do cliente não encontrado!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByNameContaining")
    public ResponseEntity<?> findByNameContaining(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'name' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        List<Client> clients = clientService.findByNameContaining(name);
        if (clients.isEmpty()) {
            return new ResponseEntity<>("Cliente com o nome/letra \"" + name + "\" não encontrado!", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/findByAgeGreaterThanEqual")
    public ResponseEntity<?> findByAgeGreaterThanEqual(@RequestParam(required = false) Integer age){
        if (age == null){
            return new ResponseEntity<>("O parâmetro 'idade' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        if (age < 0) {
            return new ResponseEntity<>("A idade deve ser um valor positivo.", HttpStatus.BAD_REQUEST);
        }
        List<Client> clients = clientService.findByAgeGreaterThanEqual(age);
        if (clients.isEmpty()) {
            return new ResponseEntity<>("Cliente com a idade \"" + age + "\" não encontrado", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }

    @GetMapping("/findByCpf")
    public ResponseEntity<?> findByCpf(@RequestParam String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'cpf' é obrigatório e não pode estar vazio.", HttpStatus.BAD_REQUEST);
        }
        Optional<Client> client = clientService.findByCpf(cpf);
        if (client.isEmpty()) {
            return new ResponseEntity<>("Cliente com o CPF: " + cpf + " não foi encontrado!", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        }
    }

    @GetMapping("/findByNameAndAge")
    public ResponseEntity<?> findByNameAndAge(@RequestParam String name, @RequestParam(required = false) Integer age) {
        if (name == null || name.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'name' é obrigatório e não pode estar vazio.", HttpStatus.BAD_REQUEST);
        }
        if (age != null && age < 0) {
            return new ResponseEntity<>("A idade deve ser um valor positivo.", HttpStatus.BAD_REQUEST);
        }
        List<Client> clients = clientService.findByNameAndAge(name, age);
        if (clients.isEmpty()) {
            return new ResponseEntity<>("Nenhum cliente encontrado com o nome \"" + name + "\" e idade igual a \"" + age + "\".", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clients, HttpStatus.OK);
        }
    }
}
