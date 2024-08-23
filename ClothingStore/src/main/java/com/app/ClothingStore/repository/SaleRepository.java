package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByAddress(String address);
 //   List<Sale> findByClient(Client client);
    //  List<Sale> findByEmployee(Employee employee);
//    List<Sale> findByTotalValueGreaterThan(Double totalValue);

//    @Query("SELECT v FROM Sale v WHERE v.client.name = :clientName AND v.totalValue > :value")
//    List<Sale> findByClientNameAndMinValue(@Param("clientName") String clientName, @Param("value") Double value);
//
//    @Query("SELECT s.client FROM Sale s GROUP BY s.client ORDER BY SUM(s.totalAmount) DESC")
//    Client findTopClientBySpending();
//
//    @Query("SELECT p FROM Product p JOIN p.sales s GROUP BY p ORDER BY SUM(s.quantity) DESC")
//    List<Product> findTopSellingProducts();

}
