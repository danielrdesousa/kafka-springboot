package com.inws.shop.validator.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.inws.shop.dtos.ShopDTO;
import com.inws.shop.dtos.ShopItemDTO;
import com.inws.shop.validator.models.Product;
import com.inws.shop.validator.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RceiveKafkaMessage {

	private static final String SHOP_TOPIC_NAME = "SHOP_TOPIC";
	private static final String SHOP_TOPIC_EVENT_NAME = "SHOP_TOPIC_EVENT";

	private final KafkaTemplate<String, ShopDTO> kafkaTemplate;

	@Autowired
	private ProductRepository productRepository;

	@KafkaListener(topics = SHOP_TOPIC_NAME, groupId = "group", containerFactory="kafkaListenerContainerFactory")
	public void listenShopTopic(ShopDTO shopDTO) {
		try {
			log.info("Compra recebida no tópico: {}.", shopDTO.getIdentifier());
			boolean success = true;

			for (ShopItemDTO item : shopDTO.getItems()) {
				Product product = productRepository.findByIdentifier(item.getProductIdentifier());
				if (!isValidShop(item, product)) {
					shopError(shopDTO);
					success = false;
					break;
				}
			}

			if (success) {
				shopSuccess(shopDTO);
			}
		} catch (Exception e) {
			log.error("Erro no processamento da compra {}", shopDTO.getIdentifier());
		}
	}

	private boolean isValidShop(ShopItemDTO item, Product product) {
		return product != null && product.getAmount() >= item.getAmount();
	}
	
	private void shopError(ShopDTO shopDTO) {
		log.error("Erro no processamento da compra. Produto Inválido {}", shopDTO.getIdentifier());
		shopDTO.setStatus("ERROR");
		kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDTO);
	}
	
	private void shopSuccess(ShopDTO shopDTO) {
		log.info("Compra {} efetuada com sucesso.", shopDTO.getIdentifier());
		 shopDTO.setStatus("SUCCESS");
		 kafkaTemplate.send(SHOP_TOPIC_EVENT_NAME, shopDTO);
	}
}
