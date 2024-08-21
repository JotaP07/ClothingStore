package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByNameContaining(String name);
    List<Employee> findByRegistration(String registration);

//    @Query("SELECT DISTINCT e FROM Employee e JOIN e.sales s")
//    List<Employee> findEmployeesWithSales();

}
