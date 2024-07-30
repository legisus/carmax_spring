package scanner.dispetchers.external;

import core.model.Car;
import core.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.utils.CarParserV2;

import java.util.List;

@Slf4j
@Component
public class AuctionDataDisp {

    private final CarService carService;
    private final CarMaxScraper cmx;

    @Autowired
    public AuctionDataDisp(CarService carService,
                           CarMaxScraper cmx) {
        this.carService = carService;
        this.cmx = cmx;
    }

    public void run() {

        List<WebElement> lanes = cmx.simulcastPageGetData();
        log.info("Lanes: {}", lanes.size());

        try {
            for (WebElement webElement : lanes) {
                if (webElement != null) {
                    List<Car> cars = CarParserV2.parseCarsFromHtml(webElement.getAttribute("outerHTML"));
                    if (cars != null) {
                        log.info("Cars found: {}", cars.size());
                        processCarData(cars);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error setting list of auctions info: {}", e.getMessage());
            log.info("* * * Start save into log * * *");
            lanes.forEach(x -> log.info(x.getAttribute("outerHTML")));
            log.info("* * * End save into log * * *");
        }
    }

    private void processCarData(List<Car> cars) {
        for (Car car : cars) {
            if (car.getSoldPrice() != null && car.getSoldPrice() > 0) {
                carService.updateSoldPriceIfValid(car.getYear(), car.getMake(), car.getModel(), car.getMileage(), car.getSoldPrice());
                log.info("Sold price updated: {}", car.getVin());
            }
        }
    }
}
