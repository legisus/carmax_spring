package scanner.dispetchers;

import core.model.Auction;
import core.model.Car;
import core.service.AuctionService;
import core.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.defenitionSteps.JdpScraper;
import scanner.defenitionSteps.MainheimScraper;
import scanner.utils.UrlLocationBuilder;
import java.util.List;

@Slf4j
@Component
public class DispatcherRunAddCarsToAuction {
    private final CarMaxScraper cmx;
    private final JdpScraper jdp;
    private final MainheimScraper mmr;
    private final AuctionService auctionService;
    private final CarService carService;

    @Autowired
    public DispatcherRunAddCarsToAuction(CarMaxScraper cmx, JdpScraper jdp, MainheimScraper mmr, AuctionService auctionService, CarService carService) {
        this.cmx = cmx;
        this.jdp = jdp;
        this.mmr = mmr;
        this.auctionService = auctionService;
        this.carService = carService;
    }

    static List<Car> newCarList;

    public void run(Auction auction) {
        Auction persistedAuction = auctionService.createOrUpdate(auction);
//        cmx.openCarMaxActionPage();
//        cmx.signInCarMax();
        cmx.openPage(UrlLocationBuilder.buildUrl(auction.getLocation()));
        cmx.closeDialogIfAppeared();
        newCarList = cmx.saveAllCarsFromElementsWithScroll();

        for (Car car : newCarList) {
            car.setAuction(persistedAuction);
            carService.addOrUpdateCar(car);
        }
    }
}
