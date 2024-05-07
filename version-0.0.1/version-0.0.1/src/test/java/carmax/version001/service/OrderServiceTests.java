package carmax.version001.service;

import carmax.version001.model.Order;
import carmax.version001.repository.OrderRepository;
import carmax.version001.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        orderService.createOrder(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder() {
        Long id = 1L;
        doNothing().when(orderRepository).deleteById(id);
        orderService.deleteOrder(id);
        verify(orderRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);
        orderService.updateOrder(order);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
//    @Order(4)
    void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<Order> result = orderService.getAllOrders();
        Assertions.assertEquals(2, result.size());
    }

    @Test
//    @Order(5)
    void testGetOrdersByUserId() {
        Long userId = 1L;
        Order order1 = new Order();
        Order order2 = new Order();
        when(orderRepository.findAllByOwnerId(userId)).thenReturn(Arrays.asList(order1, order2));
        List<Order> result = orderService.getOrdersByUserId(userId);
        Assertions.assertEquals(2, result.size());
    }

    @Test
//    @Order(6)
    void testGetOrderById() {
        Long id = 1L;
        Order order = new Order();
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));
        Optional<Order> result = orderService.getOrderById(id);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(order, result.get());
    }
}