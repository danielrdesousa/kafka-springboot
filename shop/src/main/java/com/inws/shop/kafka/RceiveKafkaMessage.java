package com.inws.shop.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inws.shop.dtos.ShopDTO;
import com.inws.shop.models.Shop;
import com.inws.shop.services.ShopService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RceiveKafkaMessage {

	private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";

    @Autowired
	private ShopService service;
    
    @KafkaListener(topics = SHOP_TOPIC_EVENT_NAME, groupId = "group", containerFactory="kafkaListenerContainerFactory")
	public void listenShopEvents(ShopDTO shopDTO) {
		try {
			log.info("Status da compra recebida no tópico: {}.", shopDTO.getIdentifier());
			Shop shop = this.service.findByIdentifier(shopDTO.getIdentifier());
			shop.setStatus(shopDTO.getStatus());
			this.service.save(shop);
			
		} catch (Exception e) {
			log.error("Erro no processamento da compra {}", shopDTO.getIdentifier());
		}
	}
}
