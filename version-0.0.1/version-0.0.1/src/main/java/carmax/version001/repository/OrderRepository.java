package carmax.version001.repository;

import carmax.version001.model.Order;
import carmax.version001.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order save(Order order);
    Order update(Order order);
    void delete(Order order);
    Optional<Order> findById(Long id);
    List<Order> getAllByOwnerId(Long userId);
    List<Order> getAllOrders();


}
