package rest.controller;

import core.model.Auction;
import core.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequestDto;
import rest.dto.LoginDto;
import scanner.dispetchers.DispatcherRunAddCarsToAuction;
import scanner.dispetchers.LoginToCarMaxPageDisp;

@RestController
@RequestMapping("/start")
public class StartController {

    private final AuctionService auctionService;
    private final DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction;
    private final LoginToCarMaxPageDisp loginToCarmaxPageDisp;

    @Autowired
    public StartController(AuctionService auctionService,
                           DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction,
                           LoginToCarMaxPageDisp loginToCarmaxPageDisp) {
        this.auctionService = auctionService;
        this.dispatcherRunAddCarsToAuction = dispatcherRunAddCarsToAuction;
        this.loginToCarmaxPageDisp = loginToCarmaxPageDisp;
    }
    @PostMapping("auction")
    public String startAuctionProcess(@Valid @RequestBody AuctionRequestDto auctionRequestDto) throws Exception {
        Auction auction = new Auction();
        auction.setLocation(auctionRequestDto.getLocation());
        auction.setDateOfAuction(auctionRequestDto.getDateOfAuction());
        auction.setTimeOfAuction(auctionRequestDto.getTimeOfAuction());

        dispatcherRunAddCarsToAuction.run(auction);
        return "Auction saved!";
    }

    @PostMapping("login")
    public String loginCarMaxPage(@Valid @RequestBody LoginDto loginDto) throws Exception {
        loginToCarmaxPageDisp.run(loginDto.getUsername(), loginDto.getPassword());
        return "Login success!";
    }
}
