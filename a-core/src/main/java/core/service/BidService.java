package core.service;

import core.model.Bid;
import core.model.Car;
import core.model.User;

import java.util.List;

public interface BidService {
    public Bid addBid(Bid bid);
    public Bid updateBid(Bid bid);
    public void deleteBid(Bid bid);
    public List<Bid> findByBidPrice(Integer bidPrice);
    public List<Bid> findByStatus(boolean status);
    public List<Bid> findByUser(User user);
    public List<Bid> findByCar(Car car);
}
