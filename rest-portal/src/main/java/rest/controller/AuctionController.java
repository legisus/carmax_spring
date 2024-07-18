package rest.controller;

import core.model.Auction;
import core.model.enums.Locations;
import core.service.AuctionService;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import scanner.dispetchers.DispatcherRunAddCarsToAuction;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    private final AuctionService auctionService;
    private final DispatcherRunAddCarsToAuction dispatcher;

    @Autowired
    public AuctionController(AuctionService auctionService, DispatcherRunAddCarsToAuction dispatcher) {
        this.auctionService = auctionService;
        this.dispatcher = dispatcher;
    }

    @PostMapping("/run")
    public ResponseEntity<String> runAuction(@RequestBody AuctionRequest request) {
        try {
            Auction auction = new Auction();
            auction.setLocation(request.getLocation());
            auction.setDateOfAuction(request.getDateOfAuction());
            auction.setTimeOfAuction(request.getTimeOfAuction());

            // Check if auction exists
            Auction existingAuction = auctionService.getAuctionByLocationAndDate(auction.getLocation(), auction.getDateOfAuction());
            if (existingAuction == null) {
                auctionService.create(auction);
            } else {
                auction = existingAuction;
            }

            // Run dispatcher
            dispatcher.run(auction);

            return ResponseEntity.ok("Auction processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process auction: " + e.getMessage());
        }
    }
}
