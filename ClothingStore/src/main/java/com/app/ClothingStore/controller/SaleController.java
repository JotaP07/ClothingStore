package com.app.ClothingStore.controller;


import com.app.ClothingStore.dto.ClientByMinSpendingDTO;
import com.app.ClothingStore.dto.ClientSpendingDTO;
import com.app.ClothingStore.dto.TopSellingProductsDTO;
import com.app.ClothingStore.entity.Client;
import com.app.ClothingStore.entity.Sale;
import com.app.ClothingStore.service.ClientService;
import com.app.ClothingStore.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Sale sale, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = saleService.save(sale);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody @Valid List<Sale> sales, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            saleService.saveAll(sales);
            return new ResponseEntity<>("Vendas salvas com sucesso!", HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid Sale sale, BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = saleService.update(sale, id);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            String message = saleService.delete(id);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<Sale> sales = saleService.findAll();
            return new ResponseEntity<>(sales, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByAddress")
    public ResponseEntity<?> findByNameContaining(@RequestParam String address) {
        try {
            List<Sale> sales = saleService.findByAddress(address);
            return new ResponseEntity<>(sales, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Sale sale = saleService.findById(id);
            return new ResponseEntity<>(sale, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<?> findByClientId(@PathVariable("clientId") Long clientId) {
        try {
            Client client = saleService.findByClientId(clientId);
            return new ResponseEntity<>(client, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TopClient")
    public ResponseEntity<?> findTopClientBySpending() {
        try {
            ClientSpendingDTO clientSpendingDTO = saleService.findTopClientBySpending();
            return new ResponseEntity<>(clientSpendingDTO, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/TopProducts")
    public ResponseEntity<?> findTopSellingProducts() {
        try {
            List<TopSellingProductsDTO> topProducts = saleService.findTopSellingProducts();
            return ResponseEntity.ok(topProducts);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/ByEmployee/{employeeId}")
    public ResponseEntity<?> findSalesByEmployeeId(@PathVariable("employeeId") Long employeeId) {
        try {
            List<Sale> sales = saleService.findSalesByEmployeeId(employeeId);
            return new ResponseEntity<>(sales, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clientsByMinSpending")
    public ResponseEntity<?> findClientsByMinSpending(@RequestParam("minValue") Double minValue) {
        try {
            List<ClientByMinSpendingDTO> clients = saleService.findClientsByMinSpending(minValue);
            return new ResponseEntity<>(clients, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


// Faltou -> Produtos vendidos em um determinado período