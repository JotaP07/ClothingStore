package com.app.ClothingStore.controller;

import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = productService.save(product);
            return new ResponseEntity<>(message, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/saveAll")
    public ResponseEntity<String> saveAll(@RequestBody @Valid List<Product> products, BindingResult result) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        try {
            productService.saveAll(products);
            return new ResponseEntity<>("Produtos salvos com sucesso!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid Product product, BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getAllErrors().get(0).getDefaultMessage();
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            String message = productService.update(product, id);
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
            String message = productService.delete(id);
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
            List<Product> products = productService.findAll();
            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByNameContaining")
    public ResponseEntity<?> findByNameContaining(@RequestParam String name) {
        try {
            List<Product> products = productService.findByNameContaining(name);
            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findByPriceGreaterThan")
    public ResponseEntity<?> findByPriceGreaterThan(@RequestParam Double price) {
        try {
            List<Product> list = productService.findByPriceGreaterThan(price);
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findProductsInRange")
    public ResponseEntity<?> findProductsInRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        try {
            List<Product> list = productService.findProductsInRange(minPrice, maxPrice);
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
