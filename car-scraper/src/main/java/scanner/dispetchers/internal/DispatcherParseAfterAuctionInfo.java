package scanner.dispetchers.internal;

import core.model.Car;
import core.model.enums.Locations;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.libraries.SimulcastScraperLibrary;
import scanner.statisticCollector.Dispatcher;
import scanner.utils.CarParser;
import scanner.utils.CarParserV2;
import utils_api.CarUtils;
import utils_api.ConstantUtils;
import utils_api.TreadUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DispatcherParseAfterAuctionInfo {

    protected CarMaxScraper cmx;
    protected SimulcastScraperLibrary simulcastScraperLibrary;

    static List<Car> carList = new ArrayList<>();

    static String location = Locations.IRVINE.getLocation();
    static LocalDate localDateOfAuction = LocalDate.of(2024, 7, 24);
    static String path = "/Users/macbook/Documents/" + localDateOfAuction + "_" + location + "_carListAfterAuction.properties";

    public DispatcherParseAfterAuctionInfo(CarMaxScraper cmx, SimulcastScraperLibrary simulcastScraperLibrary) {
        this.cmx = cmx;
        this.simulcastScraperLibrary = simulcastScraperLibrary;
    }

    public List<WebElement> getAllLanes() {
        return cmx.scrapeSimulcast();
    }

    public static void main(String[] args) {
        CarMaxScraper cmx = new CarMaxScraper();
        SimulcastScraperLibrary simulcastScraperLibrary = new SimulcastScraperLibrary();
        DispatcherParseAfterAuctionInfo dispatcher = new DispatcherParseAfterAuctionInfo(cmx, simulcastScraperLibrary);
        List<WebElement> lanes = dispatcher.getAllLanes();
        log.info("Scraped elements: " + lanes.size());

        try {
            for (WebElement webElement : lanes) {
                if (webElement != null) {
                    List<Car> cars = CarParserV2.parseCarsFromHtml(webElement.getAttribute("outerHTML"));
                    if (cars != null) {
                        carList.addAll(cars);
                        log.info("Cars found: " + carList.size());
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error setting list of auctions info: " + e.getMessage());
            log.info("* * * Start save into log * * *");
            lanes.forEach(x -> log.info(x.getAttribute("outerHTML")));
            log.info("* * * End save into log * * *");
        }

        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveCarsListToJsonFile(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);
    }

}
