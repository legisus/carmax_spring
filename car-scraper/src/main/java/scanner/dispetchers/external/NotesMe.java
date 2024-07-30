package scanner.dispetchers.external;

import core.model.Auction;
import core.model.Car;
import core.model.enums.Locations;
import core.service.AuctionService;
import core.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.utils.UrlLocationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Component
public class NotesMe {
    private final CarMaxScraper cmx;
    private final CarService carService;
    private final AuctionService auctionService;

    public NotesMe(CarMaxScraper cmx, CarService carService, AuctionService auctionService) {
        this.cmx = cmx;
        this.carService = carService;
        this.auctionService = auctionService;
    }

    public void run(Auction auction) {
        Optional<Auction> existingAuctionOptional = auctionService.getByLocationAndDate(auction.getLocation(), auction.getDateOfAuction());
        if (existingAuctionOptional.isPresent()) {
            Auction existingAuction = existingAuctionOptional.get();
            log.info("Found existing auction {}", existingAuction.getId());
            cmx.openPage(UrlLocationBuilder.biuldUrlForNotes(existingAuction));
            cmx.closeDialogIfAppeared();
            cmx.saveNotesWithEstimation(existingAuction);
        } else {
            log.info("No auction found for location: {} and date: {}", auction.getLocation(), auction.getDateOfAuction());
        }
    }
}
