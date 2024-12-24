package com.example.commande;

import com.example.commande.client.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {


    private final
    OrderRepository repository;


    private final ProductClient productClient;


    private final KafkaTemplate<String, String> kafkaTemplate;

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackCreateOrder")
    public Order createOrder(Long productId, int quantity) {
        Product product = productClient.getProduct(productId);
        if (product.getQuantity() < quantity) {
            throw new RuntimeException("Not enough stock");
        }
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        repository.save(order);

        kafkaTemplate.send("order-topic", "Order created with ID: " + order.getId());
        return order;
    }

    public Order fallbackCreateOrder(Long productId, int quantity, Throwable throwable) {
        throw new RuntimeException("Product service is unavailable");
    }
}
