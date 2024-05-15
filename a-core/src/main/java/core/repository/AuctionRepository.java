package core.repository;

import core.model.Auction;
import core.model.enums.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findByLocationAndDateOfAuction(Locations location, LocalDate date);
}
