package com.inws.shop.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inws.shop.dtos.ShopDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaClient {

	private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";

	private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

	public void sendMessage(ShopDTO shopDTO) {
		kafkaTemplate.send(SHOP_TOPIC_NAME, shopDTO);
	}
}
