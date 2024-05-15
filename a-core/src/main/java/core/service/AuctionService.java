package core.service;

import core.model.Auction;
import core.model.enums.Locations;

import java.time.LocalDate;
import java.util.List;

public interface AuctionService {
    Auction create(Auction auction);
    Auction readById(long id);
    Auction update(Auction auction);
    void delete(long id);
    List<Auction> getAll();

    Auction getAuctionByLocationAndDate(Locations location, LocalDate date);
}
