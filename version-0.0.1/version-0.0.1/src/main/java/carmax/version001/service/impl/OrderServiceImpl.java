package carmax.version001.service.impl;

import carmax.version001.model.Order;
import carmax.version001.repository.OrderRepository;
import carmax.version001.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public void updateOrder(Order newOrder) {
        orderRepository.save(newOrder);
    }

    @Override
    public void getAllOrders() {
        orderRepository.findAll();
    }

    @Override
    public void getOrdersByUserId(Long userId) {
        orderRepository.getAllByOwnerId(userId);
    }

    @Override
    public void getOrderById(Long orderId) {
        orderRepository.findById(orderId);
    }
}
