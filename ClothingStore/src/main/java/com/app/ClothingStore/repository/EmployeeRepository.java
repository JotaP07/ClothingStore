package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Employee;
import com.app.ClothingStore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
 //   public    List<Employee> findNameContaining(String name);
 //   public List<Employee> findByAgeLessThanEqual(Integer age);
//    public Optional<Employee> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT f FROM Employee f WHERE f.name LIKE %:name% AND f.age <= :age")
    public List<Employee> findByNameAndMaxAge(@Param("name") String name, @Param("age") Integer age);

   // List<Sale> findByEmployee(Employee employee);

}
