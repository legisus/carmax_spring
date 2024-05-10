package carmax.version001.service.impl;

import carmax.version001.model.Auction;
import carmax.version001.repository.AuctionRepository;
import carmax.version001.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImpl implements AuctionService {

    private final AuctionRepository auctionRepository;

    @Autowired
    public AuctionServiceImpl(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public Auction create(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction readById(long id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        return optionalAuction.orElse(null);
    }

    @Override
    public Auction update(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public void delete(long id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }
}
