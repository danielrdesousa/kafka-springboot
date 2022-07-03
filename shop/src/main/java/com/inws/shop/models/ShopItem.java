package com.inws.shop.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.inws.shop.dtos.ShopItemDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "shop_item")
public class ShopItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_identifier")
	private String productIdentifier;

	private Integer amount;

	private Float price;

	@ManyToOne
	@JoinColumn(name = "shop_id")
	private Shop shop;

	public static ShopItem convert(ShopItemDTO shopItemDTO) {  
		ShopItem shopItem = new ShopItem(); 

		shopItem.setProductIdentifier(shopItemDTO.getProductIdentifier());
		shopItem.setAmount(shopItemDTO.getAmount());
	    shopItem.setPrice(shopItemDTO.getPrice());
	    
	    return shopItem; 
	}

}
