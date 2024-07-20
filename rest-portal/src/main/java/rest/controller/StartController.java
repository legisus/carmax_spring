package rest.controller;

import core.model.Auction;
import core.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequestDto;
import rest.dto.LoginDto;
import scanner.dispetchers.external.DispatcherRunAddCarsToAuction;
import scanner.dispetchers.external.LoginMmrDisp;
import scanner.dispetchers.external.LoginToCarMaxPageDisp;
import scanner.dispetchers.external.MmrRunDisp;

@RestController
@RequestMapping("/start")
public class StartController {

    private final AuctionService auctionService;
    private final DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction;
    private final LoginToCarMaxPageDisp loginToCarmaxPageDisp;
    private final LoginMmrDisp loginMmrDisp;
    private final MmrRunDisp mmrRunDisp;

    @Autowired
    public StartController(AuctionService auctionService,
                           DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction,
                           LoginToCarMaxPageDisp loginToCarmaxPageDisp,
                           LoginMmrDisp loginMmrDisp,
                           MmrRunDisp mmrRunDisp) {
        this.auctionService = auctionService;
        this.dispatcherRunAddCarsToAuction = dispatcherRunAddCarsToAuction;
        this.loginToCarmaxPageDisp = loginToCarmaxPageDisp;
        this.loginMmrDisp = loginMmrDisp;
        this.mmrRunDisp = mmrRunDisp;
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

    @PostMapping("login/carmax")
    public String loginCarMaxPage(@Valid @RequestBody LoginDto loginDto) throws Exception {
        loginToCarmaxPageDisp.run(loginDto.getUsername(), loginDto.getPassword());
        return "Login success!";
    }

    @PostMapping("login/mmr")
    public String loginMmr(@Valid @RequestBody LoginDto loginDto) throws Exception {
        loginMmrDisp.run(loginDto.getUsername(), loginDto.getPassword());
        return "Login success!";
    }

    @GetMapping("mmr")
    public String mmr() {
        mmrRunDisp.run();
        return "MMR updated success!";
    }
}
