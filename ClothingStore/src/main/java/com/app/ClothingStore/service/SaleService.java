package com.app.ClothingStore.service;

import com.app.ClothingStore.entity.Product;
import com.app.ClothingStore.entity.Sale;
import com.app.ClothingStore.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    public String save(Sale sale) {
        saleRepository.save(sale);
        return "Venda com ID " + sale.getId() + " salvo com sucesso!";
    }

    public void saveAll(List<Sale> sale){
        saleRepository.saveAll(sale);
    }

    public String update(Sale sale, Long id) {
        if (saleRepository.existsById(id)) {
            sale.setId(id);
            saleRepository.save(sale);
            return "Venda com o  ID " + sale.getId() + " atualizado com sucesso!";
        } else {
            return "Venda não encontrado!";
        }
    }

    public String delete(Long id) {
        if (saleRepository.existsById(id)) {
            saleRepository.deleteById(id);
            return "Venda com ID " + id + " deletado com sucesso!";
        } else {
            return "Venda com ID " + id + " não encontrado!";
        }
    }
}
