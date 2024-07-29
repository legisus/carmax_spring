package scanner.dispetchers.external;

import core.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.libraries.SimulcastScraperLibrary;

@Slf4j
@Component
public class AuctionDataDisp {

    private final CarService carService;
    private final CarMaxScraper cmx;
    private final SimulcastScraperLibrary simulcastScraperLibrary;

    @Autowired
    public AuctionDataDisp(CarService carService,
                           CarMaxScraper cmx,
                           SimulcastScraperLibrary simulcastScraperLibrary) {
        this.carService = carService;
        this.cmx = cmx;
        this.simulcastScraperLibrary = simulcastScraperLibrary;
    }

    public void run() {

    }
}
