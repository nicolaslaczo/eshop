package com.nicolas.eshop.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    Optional<Product> findByProductNum(String productNum);

    List<Product> findByCategoryId(Integer categoryId);

    @Override
    Page<Product> findAll(Pageable pageable);


























}
