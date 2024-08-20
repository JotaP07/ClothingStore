package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContaining(String name);
    Optional<Client> findByCpf(String cpf);
    List<Client> findByAgeGreaterThanEqual(Integer age);

    @Query("SELECT c FROM Client c WHERE c.name LIKE %:name% AND c.age = :age")
    List<Client> findByNameAndAge(@Param("name") String name, @Param("age") Integer age);
}
