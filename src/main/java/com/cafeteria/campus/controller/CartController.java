package com.cafeteria.campus.controller;

import com.cafeteria.campus.model.*;
import com.cafeteria.campus.repository.CartRepository;
import com.cafeteria.campus.repository.ProductRepository;
import com.cafeteria.campus.repository.OrderRepository;

import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin
public class CartController {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CartController(CartRepository cartRepository,
                          ProductRepository productRepository,
                          OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // Obtener carrito por usuario
    @GetMapping("/{userId}")
    public Cart getCart(@PathVariable String userId) {

        return cartRepository.findByUserIdAndStatus(userId, "OPEN")
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUserId(userId);
                    c.setStatus("OPEN");
                    c.setCreatedAt(Instant.now());
                    c.setItems(new ArrayList<>());
                    return cartRepository.save(c);
                });
    }

    // Agregar item
    @PostMapping("/{userId}/items")
    public Cart addItem(@PathVariable String userId, @RequestBody AddItemRequest req) {

        Cart cart = getCart(userId);

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        List<OrderItem> items = cart.getItems();
        boolean found = false;

        for (OrderItem item : items) {
            if (item.getProductId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + req.getQuantity());
                found = true;
                break;
            }
        }

        if (!found) {
            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setUnitPrice(product.getPrice());
            item.setQuantity(req.getQuantity());
            items.add(item);
        }

        cart.setItems(items);
        return cartRepository.save(cart);
    }

    // Eliminar item
    @DeleteMapping("/{userId}/items/{productId}")
    public Cart removeItem(@PathVariable String userId, @PathVariable String productId) {

        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        return cartRepository.save(cart);
    }

    // Checkout (crear Order)
    @PostMapping("/{userId}/checkout")
    public Order checkout(@PathVariable String userId) {

        Cart cart = cartRepository.findByUserIdAndStatus(userId, "OPEN")
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        double total = cart.getItems().stream()
                .mapToDouble(i -> i.getUnitPrice() * i.getQuantity())
                .sum();

        Order o = new Order();
        o.setUserId(userId);
        o.setItems(cart.getItems());
        o.setTotal(total);
        o.setStatus("PAID");
        o.setPaymentMethod("CASH");
        o.setCreatedAt(Instant.now());

        orderRepository.save(o);

        cart.setStatus("CHECKED_OUT");
        cartRepository.save(cart);

        return o;
    }

    // Request para agregar items
    static class AddItemRequest {
        private String productId;
        private int quantity;

        public String getProductId() { return productId; }
        public int getQuantity() { return quantity; }
    }
}
