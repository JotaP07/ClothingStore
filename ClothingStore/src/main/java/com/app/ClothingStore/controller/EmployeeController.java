package com.app.ClothingStore.controller;

import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Employee employee) {
        try {
            String message= employeeService.save(employee);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar o funcionário! " + e.getMessage(),  HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody List<Employee> employee){
        try {
            employeeService.saveAll(employee);
            return new ResponseEntity<>("Funcionários salvos com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao salvar os funcionários! " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @Valid @RequestBody Employee employee, BindingResult result) { //BindingResult armazena o resultado, caso errado trata corretamente e cai no if
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = employeeService.update(employee, id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao alterar o funcionário " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String mensagem = employeeService.delete(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao deletar o funcionário " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<Employee> employees = employeeService.findAll();
            if (employees.isEmpty()) {
                return new ResponseEntity<>("Nenhum funcionário encontrado!", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar os funcionários! " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        if (id != null && id < 0) {
            return new ResponseEntity<>("O ID não deve ser negativo!", HttpStatus.BAD_REQUEST);
        }
        try {
            Employee employee = this.employeeService.findById(id);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nenhum funcionário encontrado!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar o funcionário!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
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
            List<Employee> employees = employeeService.findByNameContaining(name);
            if (employees != null) {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Funcionário com o nome/letra \"" + name + "\" não encontrado!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar os funcionários!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByRegistration")
    public ResponseEntity<?> findByRegistration(@RequestParam String registration) {
        if (registration == null || registration.trim().isEmpty()) {
            return new ResponseEntity<>("O parâmetro 'registration' é obrigatório e não pode estar vazio!", HttpStatus.BAD_REQUEST);
        }
        try {
            List<Employee> employees = employeeService.findByRegistration(registration);
            if (employees.isEmpty()) {
                return new ResponseEntity<>("Funcionário com o registro \"" + registration + "\" não encontrado!", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao buscar o registro!" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
