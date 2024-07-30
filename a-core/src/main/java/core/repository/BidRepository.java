package core.repository;


import core.model.Bid;
import core.model.Car;
import core.model.User;
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
