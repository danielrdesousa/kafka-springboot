package com.inws.shop.services;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inws.shop.dtos.ShopDTO;
import com.inws.shop.kafka.KafkaClient;
import com.inws.shop.models.Shop;
import com.inws.shop.models.ShopItem;
import com.inws.shop.repositories.ShopRepository;


@Service
public class ShopService {

    @Autowired
    private ShopRepository repository;

    @Autowired
    private KafkaClient kafkaClient;
    
    public List<ShopDTO> findAll() {
		return this.repository.findAll().stream().map(shop -> ShopDTO.convert(shop)).collect(Collectors.toList());
    }

    public ShopDTO buy(ShopDTO shopDTO) {
		shopDTO.setIdentifier(UUID.randomUUID().toString()); 
		shopDTO.setDateShop(LocalDate.now());
		shopDTO.setStatus("PENDING");
		
        Shop shop = Shop.convert(shopDTO);
        
        for (ShopItem shopItem : shop.getItems()) { 
        	shopItem.setShop(shop); 
        }
        
        shopDTO = ShopDTO.convert(this.repository.save(shop));
        kafkaClient.sendMessage(shopDTO); 

        return shopDTO;
    }
    
    public void delete(Long id) {
    	this.repository.deleteById(id);
    }

	public ShopDTO findById(Long id) {
        Shop shop = this.repository.findById(id).orElseThrow(null);
        ShopDTO shopDTO = ShopDTO.convert(shop);
        
        return shopDTO;
	}

	public Shop findByIdentifier(String identifier) {
		Shop shop = this.repository.findByIdentifier(identifier);        
        return shop;
	}
	
	public Shop save(Shop shop) {
		return this.repository.save(shop);        
	}
 
}
