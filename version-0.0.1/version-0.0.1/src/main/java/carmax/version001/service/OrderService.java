package carmax.version001.service;

import carmax.version001.model.Order;

import java.util.List;
import java.util.Optional;


public interface OrderService {
    void createOrder(Order order);
    void deleteOrder(Long orderId);
    void updateOrder(Order newOrder);
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(Long userId);
    Optional<Order> getOrderById(Long orderId);
}
