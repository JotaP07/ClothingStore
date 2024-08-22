package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Sale;
import com.app.ClothingStore.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public String save(Sale sale) {
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }
        if (sale.getClient() == null || sale.getEmployee() == null || sale.getProducts() == null || sale.getProducts().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter um cliente, um funcionário e pelo menos um produto associado!");
        }
        saleRepository.save(sale);
        return "Venda com ID " + sale.getId() + " salva com sucesso!";
    }

    public void saveAll(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            throw new IllegalArgumentException("A lista de vendas não pode ser nula ou vazia!");
        }
        for (Sale sale : sales) {
            if (sale == null) {
                throw new IllegalArgumentException("Venda não pode ser nula na lista!");
            }
            if (sale.getClient() == null || sale.getEmployee() == null || sale.getProducts() == null || sale.getProducts().isEmpty()) {
                throw new IllegalArgumentException("Cada venda na lista deve ter um cliente, um funcionário e pelo menos um produto associado!");
            }
        }
        saleRepository.saveAll(sales);
    }

    public String update(Sale sale, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um valor positivo!");
        }
        if (sale == null) {
            throw new IllegalArgumentException("A venda não pode ser nula!");
        }
        if (sale.getClient() == null || sale.getEmployee() == null || sale.getProducts() == null || sale.getProducts().isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter um cliente, um funcionário e pelo menos um produto associado!");
        }
        if (saleRepository.existsById(id)) {
            sale.setId(id);
            saleRepository.save(sale);
            return "Venda com o ID " + sale.getId() + " atualizada com sucesso!";
        } else {
            throw new EntityNotFoundException("Venda com o ID " + id + " não encontrada!");
        }
    }

    public String delete(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID deve ser um valor positivo!");
        }
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return "Venda com ID " + id + " deletada com sucesso!";
        } else {
            throw new EntityNotFoundException("Venda com ID " + id + " não encontrada!");
        }
    }
}
