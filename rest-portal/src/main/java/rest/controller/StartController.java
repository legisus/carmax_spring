package rest.controller;

import core.model.Auction;
import core.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequestDto;
import rest.dto.FilePathDto;
import rest.dto.LoginDto;
import scanner.dispetchers.external.*;

@RestController
@RequestMapping("/start")
public class StartController {

    private final AuctionService auctionService;
    private final DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction;
    private final LoginToCarMaxPageDisp loginToCarmaxPageDisp;
    private final LoginMmrDisp loginMmrDisp;
    private final MmrRunDisp mmrRunDisp;
    private final JdpRunDisp jdpRunDisp;
    private final AuctionDataDisp auctionDataDisp;
    private final SimulcastDisp simulcastDisp;
    private final ConvertHtmlToJsonDisp convertHtmlToJsonDisp;
    private final NotesMe notesMe;

    @Autowired
    public StartController(AuctionService auctionService,
                           DispatcherRunAddCarsToAuction dispatcherRunAddCarsToAuction,
                           LoginToCarMaxPageDisp loginToCarmaxPageDisp,
                           LoginMmrDisp loginMmrDisp,
                           MmrRunDisp mmrRunDisp, JdpRunDisp jdpRunDisp, AuctionDataDisp auctionDataDisp, SimulcastDisp simulcastDisp, ConvertHtmlToJsonDisp convertHtmlToJsonDisp, NotesMe notesMe) {
        this.auctionService = auctionService;
        this.dispatcherRunAddCarsToAuction = dispatcherRunAddCarsToAuction;
        this.loginToCarmaxPageDisp = loginToCarmaxPageDisp;
        this.loginMmrDisp = loginMmrDisp;
        this.mmrRunDisp = mmrRunDisp;
        this.jdpRunDisp = jdpRunDisp;
        this.auctionDataDisp = auctionDataDisp;
        this.simulcastDisp = simulcastDisp;
        this.convertHtmlToJsonDisp = convertHtmlToJsonDisp;
        this.notesMe = notesMe;
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

    @PostMapping("notes")
    public String putMeEstimationNotes(@Valid @RequestBody AuctionRequestDto auctionRequestDto) throws Exception {
        Auction auction = new Auction();
        auction.setLocation(auctionRequestDto.getLocation());
        auction.setDateOfAuction(auctionRequestDto.getDateOfAuction());
        auction.setTimeOfAuction(auctionRequestDto.getTimeOfAuction());

        notesMe.run(auction);
        return "Notes successfully updated!";
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

    @GetMapping("jdp")
    public String jdp() {
        jdpRunDisp.run();
        return "JDP estimations updated success!";
    }

//    @PostMapping("data-scrap")
//    public String dataScrap(@Valid @RequestBody AuctionRequestDto auctionRequestDto) throws Exception {
//        Auction auction = new Auction();
//        auction.setLocation(auctionRequestDto.getLocation());
//        auction.setDateOfAuction(auctionRequestDto.getDateOfAuction());
//        auction.setTimeOfAuction(auctionRequestDto.getTimeOfAuction());
//
//        auctionDataDisp.run(auction);
//        return "Auction saved!";
//    }

    @GetMapping("simulcast-run")
    public String simulcast() {
        auctionDataDisp.run();
        return "Data scrap updated success!";
    }

    @GetMapping("data-scrap")
    public String dataScrap() {
        auctionDataDisp.run();
        return "Data scrap updated success!";
    }

    @PostMapping("convert")
    public String convertHtmlToJsonFile(@Valid @RequestBody FilePathDto filePathDto) throws Exception {
        return convertHtmlToJsonDisp.convertHtmlFileToJsonFile(filePathDto.getInputFilePath(), filePathDto.getOutputFilePath());
    }

}
