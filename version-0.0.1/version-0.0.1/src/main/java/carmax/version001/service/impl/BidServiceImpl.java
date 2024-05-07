package carmax.version001.service.impl;


import carmax.version001.model.Bid;
import carmax.version001.model.Car;
import carmax.version001.model.User;
import carmax.version001.repository.BidRepository;
import carmax.version001.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidServiceImpl implements BidService {

    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid addBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public Bid updateBid(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public void deleteBid(Bid bid) {
        bidRepository.delete(bid);
    }

    @Override
    public List<Bid> findByBidPrice(Integer bidPrice) {
        return bidRepository.findByBidPrice(bidPrice);
    }

    @Override
    public List<Bid> findByStatus(boolean status) {
        return bidRepository.findByStatus(status);
    }

    @Override
    public List<Bid> findByUser(User user) {
        return bidRepository.findByUser(user);
    }

    @Override
    public List<Bid> findByCar(Car car) {
        return bidRepository.findByCar(car);
    }
}
