package core.service;

import core.model.Auction;
import core.model.enums.Locations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AuctionService {
    Auction createOrUpdate(Auction auction);
    Auction readById(long id);
    Auction update(Auction auction);
    void delete(long id);
    List<Auction> getAll();

    Optional<Auction> getByLocationAndDate(Locations location, LocalDate date);
}
