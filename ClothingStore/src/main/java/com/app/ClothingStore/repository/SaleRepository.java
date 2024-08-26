package com.app.ClothingStore.repository;

import com.app.ClothingStore.dto.ClientByMinSpendingDTO;
import com.app.ClothingStore.dto.ClientSpendingDTO;
import com.app.ClothingStore.dto.TopSellingProductsDTO;
import com.app.ClothingStore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByAddress(String address);
    List<Sale> findByClientId(Long clientId); ;


    @Query("SELECT new com.app.ClothingStore.dto.ClientSpendingDTO(c.id, c.name, SUM(s.totalValue)) " +
            "FROM Sale s " +
            "JOIN s.client c " +
            "GROUP BY c.id, c.name " +
            "ORDER BY SUM(s.totalValue) DESC")
    List<ClientSpendingDTO> findClientsOrderedBySpending();


    @Query("SELECT new com.app.ClothingStore.dto.TopSellingProductsDTO(p.id, p.name, p.price, SUM(s.totalValue))" +
            "From Sale s " +
            "JOIN s.products p " +
            "GROUP BY p.id, p.name, p.price " +
            "ORDER BY SUM(s.totalValue) DESC")
    List<TopSellingProductsDTO> findTopSellingProducts();

    @Query("SELECT s FROM Sale s WHERE s.employee.id = :employeeId")
    List<Sale> findSalesByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT new com.app.ClothingStore.dto.ClientByMinSpendingDTO(c.id, c.name, c.cpf, c.age, c.telephone, SUM(s.totalValue)) " +
            "FROM Sale s " +
            "JOIN s.client c " +
            "GROUP BY c.id, c.name, c.cpf, c.age, c.telephone " +
            "HAVING SUM(s.totalValue) > :minValue " +
            "ORDER BY SUM(s.totalValue) DESC")
    List<ClientByMinSpendingDTO> findClientsByMinSpending(@Param("minValue") Double minValue);






}
