package com.inws.shop.validator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inws.shop.validator.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	 
	Product findByIdentifier(String identifier);
	
}
