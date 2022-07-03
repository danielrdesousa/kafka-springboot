package com.inws.shop.models;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.inws.shop.dtos.ShopDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Shop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String identifier;
	
	private String status;
	
	@Column(name = "date_shop") 
	private LocalDate dateShop;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "shop")
	private List<ShopItem> items;

	public static Shop convert(ShopDTO shopDTO) { 
		Shop shop = new Shop();
		
		shop.setIdentifier(shopDTO.getIdentifier());
		shop.setStatus(shopDTO.getStatus());
		shop.setDateShop(shopDTO.getDateShop()); 
        shop.setItems(shopDTO 
                .getItems() 
                .stream() 
                .map(i -> ShopItem.convert(i)) 
                .collect(Collectors.toList())
        );
        
        return shop; 
	}
}
