package com.jane.ws.products.service;

import com.jane.ws.core.ProductCreatedEvent;
import com.jane.ws.products.rest.CreateProductRestModel;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{
    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception{
        String productId = UUID.randomUUID().toString();

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
                productRestModel.getTitle(),
                productRestModel.getPrice(), productRestModel.getQuantity()
        );
        LOGGER.info("Before publishing a ProductRecordEvent");
//        ************************** Synchronous**********************


        ProducerRecord<String, ProductCreatedEvent> record = new ProducerRecord<>(
                "products-created-events-topic",
                productId,
                productCreatedEvent
        );
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send(record).get();

//        **************************************************************

//        ************ Asynchronous **********************
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send("products-created-events-topic", productId, productCreatedEvent);
//
//        future.whenComplete((result, exception) -> {
//            if(exception != null){
//                LOGGER.error("********Failed to send Message: " + exception.getMessage());
//            } else{
//                LOGGER.info("********Message sent successfully: " + result.getRecordMetadata());
//            }
//        });

//        ********************************************

//        *************synchronous************
//        future.join();
//        ************************************

        LOGGER.info("Partition: " + result.getRecordMetadata().partition());
        LOGGER.info("Topic: " + result.getRecordMetadata().topic());
        LOGGER.info("Offset: " + result.getRecordMetadata().offset());
        LOGGER.info("********************** Returning Product ID");
        return productId;
    }
}
