package com.app.ClothingStore.repository;

import com.app.ClothingStore.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByNameContaining(String name);
    List<Client> findByCpf(String cpf);

    //JPQL | %.% significa "qualquer coisa antes e depois do valor".
    @Query("SELECT c FROM Client c WHERE c.name LIKE %:name% AND c.age = :age")
    List<Client> findByNameAndAge(@Param("name") String name, @Param("age") Integer age);
}
