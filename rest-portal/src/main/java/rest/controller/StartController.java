package rest.controller;

import core.model.Auction;
import core.model.enums.Locations;
import core.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scanner.dispetchers.DispatcherRunAddCarsToAuction;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/start")
public class StartController {

    private final AuctionService auctionService;
    private final DispatcherRunAddCarsToAuction dispatcher;

    @Autowired
    public StartController(AuctionService auctionService, DispatcherRunAddCarsToAuction dispatcher) {
        this.auctionService = auctionService;
        this.dispatcher = dispatcher;
    }
    @GetMapping
    public String startAuctionProcess() throws Exception {

        Auction auction = new Auction();
        auction.setLocation(Locations.EL_PASO);
        auction.setDateOfAuction(LocalDate.of(2024, 7, 1));
        auction.setTimeOfAuction(LocalTime.of(11, 0));

        dispatcher.run(auction);
        return "Auction process started!";
    }
}
