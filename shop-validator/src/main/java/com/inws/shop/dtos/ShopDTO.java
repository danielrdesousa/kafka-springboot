package com.inws.shop.dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopDTO {
	
	private String identifier;
	private LocalDate dateShop;
	private String status;
	private List<ShopItemDTO> items = new ArrayList<>(); 
	
}
