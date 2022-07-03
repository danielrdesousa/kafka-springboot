package com.inws.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inws.shop.models.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
	
	public Shop findByIdentifier(String identifier); 

}
