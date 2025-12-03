package com.utkrusht.marketplace.repository;

import com.utkrusht.marketplace.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Only loads everything needed for admin view (if you ever map supplier entity/contract as relation, add EntityGraph here)
    @Query("SELECT p FROM Product p")
    List<Product> findAllWithSupplierDetails();

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findByIdWithSupplierDetails(Long id);
}
