package carmax.version001.controller;

import carmax.version001.model.Auction;
import carmax.version001.model.carInner.Locations;
import carmax.version001.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AuctionController {

    private final AuctionService auctionService;

    @Autowired
    AuctionController (AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/auctions")
    public String showAuctions(Model model) {
        List<Auction> auctions = auctionService.getAll();


        List<Locations> locations = auctions.stream()
                .map(Auction::getLocation)
                .toList();
        model.addAttribute("locations", locations);
        return "auctions";  // Name of your HTML view
    }
}

