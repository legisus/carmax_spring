package core.repository;

import core.model.Auction;
import core.model.enums.Locations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
//
//    @Query("SELECT a FROM Auction a LEFT JOIN FETCH a.cars WHERE a.location = :location AND a.dateOfAuction = :date")
//    Auction findByLocationAndDateOfAuction(@Param("location") Locations location, @Param("date") LocalDate date);
////    Auction findByLocationAndDateOfAuction(Locations location, LocalDate date);

    @Query("SELECT a FROM Auction a LEFT JOIN FETCH a.cars WHERE a.location = :location AND a.dateOfAuction = :date")
    Optional<Auction> findByLocationAndDateOfAuction(@Param("location") Locations location, @Param("date") LocalDate date);
}
