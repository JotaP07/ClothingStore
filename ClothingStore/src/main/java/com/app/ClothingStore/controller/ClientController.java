package com.app.ClothingStore.controller;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.service.ClientService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.br.CPF;
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
            String message = clientService.save(client);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar o cliente! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody List<Client> clients) {
        try {
            clientService.saveAll(clients);
            return new ResponseEntity<>("Clientes salvos com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar os clientes! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Client client, BindingResult result) { //BindingResult armazena o resultado, caso errado trata corretamente e cai no if
        if (result.hasErrors()) {
            String error = result.getAllErrors().get(0).getDefaultMessage();// isso aqui retorna a msg de erro
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = clientService.update(client, id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao alterar o cliente! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String message = clientService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro erro ao deletar o cliente! " + e.getMessage(), HttpStatus.BAD_REQUEST);
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
            return new ResponseEntity<>("Erro ao buscar os clientes! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id != null && id < 0) {
            return new ResponseEntity<>("O ID não deve ser negativo!", HttpStatus.BAD_REQUEST);
        }
        try {
            Client client = this.clientService.findById(id);
            if (client != null) {
                return new ResponseEntity<>(client, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nenhum cliente encontrado!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar o cliente!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByNameContaining")
    public ResponseEntity<?> findByNameContaining(@RequestParam String name) {
        if (name == null || name.trim().isEmpty()) { //isEmpty garante q a string nao esteja vazia
            return new ResponseEntity<>("O parâmetro 'name' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        if (name.matches("\\d+")) { // verifica se o parametro apenas numeros
            return new ResponseEntity<>("O parâmetro 'name' não pode ser um número!", HttpStatus.BAD_REQUEST);
        }
        try {
            List<Client> clients = clientService.findByNameContaining(name);
            if (clients != null) {
                return new ResponseEntity<>(clients, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cliente com o nome/letra \"" + name + "\" não encontrado!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar os clientes!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByCpf")
    public ResponseEntity<?> findByCpf(@Valid @RequestParam String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'cpf' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        //validando o cpf no request
        String cpfRegex = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        if (!cpf.matches(cpfRegex)) { // 'matches' verifica se a string corresponde à expressão regular fornecida
            return new ResponseEntity<>("O CPF fornecido não está no formato correto!", HttpStatus.BAD_REQUEST);
        }
        try {
            List<Client> clients = this.clientService.findByCpf(cpf);
            if (clients.isEmpty()) {
                return new ResponseEntity<>("Nenhum cliente encontrado com o CPF fornecido!", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(clients, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar o cliente!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByNameAndAge")
    public ResponseEntity<?> findByNameAndAge(@RequestParam String name, @RequestParam(required = false) Integer age) {
        if (name == null || name.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'name' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        if (age != null && age < 0) {
            return new ResponseEntity<>("A idade deve ser um valor positivo!", HttpStatus.BAD_REQUEST);
        }
        try {
            List<Client> clients = clientService.findByNameAndAge(name, age);
            if (clients.isEmpty()) {
                return new ResponseEntity<>("Nenhum cliente encontrado com o nome \"" + name + "\" e idade igual a \"" + age + "\"!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(clients, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar os clientes." + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
