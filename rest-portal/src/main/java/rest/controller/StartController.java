package rest.controller;

import core.model.Auction;
import core.model.enums.Locations;
import core.service.AuctionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.dto.AuctionRequestDto;
import rest.dto.FilePathDto;
import rest.dto.LoginDto;
import scanner.dispetchers.external.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @GetMapping("/locations")
    public List<String> getLocations() {
        return Arrays.stream(Locations.values())
                .map(Locations::getLocation)
                .collect(Collectors.toList());
    }

    @PostMapping("login/carmax")
    public ResponseEntity<String> loginCarMaxPage(@Valid @RequestBody LoginDto loginDto) {
        try {
            loginToCarmaxPageDisp.run(loginDto.getUsername(), loginDto.getPassword());
            return ResponseEntity.ok("Login success!");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
        }
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

    @GetMapping("simulcast-run")
    public String simulcast() {
        simulcastDisp.run();
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}
