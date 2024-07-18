package scanner.dispetchers;

import core.model.Auction;
import core.model.Car;
import core.service.AuctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.defenitionSteps.JdpScraper;
import scanner.defenitionSteps.MainheimScraper;
import utils_api.CarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class DispatcherRunAddCarsToAuction {
    private final CarMaxScraper cmx;
    private final JdpScraper jdp;
    private final MainheimScraper mmr;
    private final AuctionService auctionService;

    @Autowired
    public DispatcherRunAddCarsToAuction(CarMaxScraper cmx, JdpScraper jdp, MainheimScraper mmr, AuctionService auctionService) {
        this.cmx = cmx;
        this.jdp = jdp;
        this.mmr = mmr;
        this.auctionService = auctionService;
    }

    public void run(Auction auction) throws Exception {
        // Add your existing logic here
        auctionService.create(auction);

        Set<Car> setCars = auctionService.getAuctionByLocationAndDate(auction.getLocation(),
                auction.getDateOfAuction()).getCars();

        List<Car> carList = new ArrayList<>(setCars);

        //*************************1.CARMAX***************************

        cmx.openCarMaxActionPage();
        cmx.signInCarMax();
        cmx.openPage(auction.getLocation().getLocation());
        cmx.closeDialogIfAppeared();

        List<Car> newCarList = cmx.saveAllCarsFromElementsWithScroll();

        if (carList == null) carList = newCarList;

        if (carList.size() != newCarList.size()) {
            log.info("New cars were added to the list");
            List<Car> uniqueCars = CarUtils.getUniqueCars(carList, newCarList);
            log.info("Unique cars: " + uniqueCars.size());

            uniqueCars.forEach(car -> log.info(car.toString()));

            carList.addAll(uniqueCars);
        }

        carList.forEach(car -> car.setAuction(auction));

        auction.setCars(Set.copyOf(carList));
        auctionService.update(auction);
    }
}
