package carmax.version001.repository;


import carmax.version001.model.Bid;
import carmax.version001.model.Car;
import carmax.version001.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByBidPrice(Integer bidPrice);
    List<Bid> findByStatus(boolean status);
    List<Bid> findByUser(User user);
    List<Bid> findByCar(Car car);
}
