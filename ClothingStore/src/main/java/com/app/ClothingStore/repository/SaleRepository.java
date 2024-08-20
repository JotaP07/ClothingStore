package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.entity.Sale;
import com.app.ClothingStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
 //   List<Sale> findByClient(Client client);
  //  List<Sale> findByEmployee(Employee employee);
//    List<Sale> findByTotalValueGreaterThan(Double totalValue);

    @Query("SELECT v FROM Sale v WHERE v.client.name = :clientName AND v.totalValue > :value")
    List<Sale> findByClientNameAndMinValue(@Param("clientName") String clientName, @Param("value") Double value);
}
