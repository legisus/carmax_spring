package carmax.version001.service;

import carmax.version001.model.Order;
import org.springframework.stereotype.Service;


public interface OrderService {
    void createOrder(Order order);
    void deleteOrder(Long orderId);
    void updateOrder(Order newOrder);
    void getAllOrders();
    void getOrdersByUserId(Long userId);
    void getOrderById(Long orderId);
}
