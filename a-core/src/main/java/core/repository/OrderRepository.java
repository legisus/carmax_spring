package core.repository;

import core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    void delete(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAllByOwnerId(Long userId);
}
