package core.service;

import core.model.Auction;

import java.util.List;

public interface AuctionService {
    Auction create(Auction auction);
    Auction readById(long id);
    Auction update(Auction auction);
    void delete(long id);
    List<Auction> getAll();
}