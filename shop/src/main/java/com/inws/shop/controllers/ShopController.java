package com.inws.shop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inws.shop.dtos.ShopDTO;
import com.inws.shop.models.Shop;
import com.inws.shop.services.ShopService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
	private ShopService service;
    
	@GetMapping
	public List<ShopDTO> findAll() {
		return this.service.findAll();
	}
	
	@PostMapping
	public ShopDTO save(@RequestBody ShopDTO shopDTO) {
		return this.service.buy(shopDTO);
	}

    @GetMapping("/{shopIdentifier}")
    private ShopDTO findById(@PathVariable("shopIdentifier") String identifier) {
        Shop shop = this.service.findByIdentifier(identifier);
        ShopDTO shopDTO = ShopDTO.convert(shop);
        
        return shopDTO;
    }
    
    @DeleteMapping("/{id}")
    private void deletePerson(@PathVariable("id") Long id) {
        this.service.delete(id);
    }
}
