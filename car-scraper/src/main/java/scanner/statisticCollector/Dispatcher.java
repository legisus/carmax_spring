package scanner.statisticCollector;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.libraries.SimulcastScraperLibrary;


import java.time.LocalDate;
import java.util.List;

@Slf4j
public class Dispatcher {

    static LocalDate dateOfAuction = LocalDate.of(2024, 4, 29);

    protected CarMaxScraper cmx;
    protected SimulcastScraperLibrary simulcastScraperLibrary;

    public Dispatcher(CarMaxScraper cmx, SimulcastScraperLibrary simulcastScraperLibrary) {
        this.cmx = cmx;
        this.simulcastScraperLibrary = simulcastScraperLibrary;
    }

    public List<WebElement> getAllLanes() {
        return cmx.scrapeSimulcast();
    }

    public static void main(String[] args) {
        CarMaxScraper cmx = new CarMaxScraper();
        SimulcastScraperLibrary simulcastScraperLibrary = new SimulcastScraperLibrary();
        Dispatcher dispatcher = new Dispatcher(cmx, simulcastScraperLibrary);
        List<WebElement> lanes = dispatcher.getAllLanes();
        log.info("Scraped elements: " + lanes.size());

        try {
//            simulcastScraperLibrary.writeListOfAuctionsInfoToFile(lanes,
//                    CarMaxAuctionCredentialsUtils.URI_SAVE_WEB_ELEMENTS_SIMULCAST + dateOfAuction + "_auctions_data.txt");
//            log.info("List of auctions info saved successfully");
        } catch (Exception e) {
            log.error("Error setting list of auctions info: " + e.getMessage());
            log.info("* * * Start save into log * * *");
            lanes.forEach(x-> log.info(x.getAttribute("outerHTML")));
            log.info("* * * End save into log * * *");
        }
    }
}
