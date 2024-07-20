package rest.controller;

import core.model.Auction;
import core.service.AuctionService;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import scanner.dispetchers.DispatcherRunAddCarsToAuction;

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
    public ResponseEntity<String> runAuction(@RequestBody AuctionRequestDto request) {
        try {
            Auction auction = new Auction();
            auction.setLocation(request.getLocation());
            auction.setDateOfAuction(request.getDateOfAuction());
            auction.setTimeOfAuction(request.getTimeOfAuction());

            // Check if auction exists
//            Auction existingAuction = auctionService.getByLocationAndDate(auction.getLocation(), auction.getDateOfAuction());
//            if (existingAuction == null) {
//                auctionService.createOrUpdate(auction);
//            } else {
//                auction = existingAuction;
//            }

            // Run dispatcher
            dispatcher.run(auction);

            return ResponseEntity.ok("Auction processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to process auction: " + e.getMessage());
        }
    }
}
