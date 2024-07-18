package scanner.dispetchers;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.defenitionSteps.JdpScraper;
import scanner.defenitionSteps.MainheimScraper;
import core.model.Auction;
import core.model.Car;
import core.model.enums.Locations;
import core.service.AuctionService;
import core.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import scanner.utils.UrlLocationBuilder;
import utils_api.CarUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"core", "scanner"})
@EnableJpaRepositories(basePackages = "core.repository")
public class DispatcherAddCarsToAuction {


    protected CarMaxScraper cmx;
    protected JdpScraper jdp;
    protected MainheimScraper mmr;

    private static final Auction auction = new Auction();

    @Autowired
    public DispatcherAddCarsToAuction(CarMaxScraper cmx, JdpScraper jdp, MainheimScraper mmr) {
        this.cmx = cmx;
        this.jdp = jdp;
        this.mmr = mmr;
    }

    static List<Car> carList;
    static List<Car> newCarList;
    static List<Car> uniqueCars;


    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(DispatcherAddCarsToAuction.class, args);
//        SpringApplication.run(DispatcherAddCarsToAuction.class, args);

        auction.setLocation(Locations.MURRIETA);
        auction.setDateOfAuction(LocalDate.of(2024, 7, 15));
        auction.setTimeOfAuction(LocalTime.of(9, 0));

        AuctionService auctionService = context.getBean(AuctionService.class);
        CarService carService = context.getBean(CarService.class);
        DispatcherAddCarsToAuction dispatcher = context.getBean(DispatcherAddCarsToAuction.class);
//        DispatcherAddCarsToAuction dispatcher = new DispatcherAddCarsToAuction(new CarMaxScraper(), new JdpScraper(), new MainheimScraper());

        auctionService.create(auction);

        Set<Car> setCars = auctionService.getAuctionByLocationAndDate(auction.getLocation(),
                auction.getDateOfAuction()).getCars();

        carList = new ArrayList<>(setCars);

//        //*************************1.CARMAX***************************

        dispatcher.cmx.openCarMaxActionPage();
        dispatcher.cmx.signInCarMax();
        dispatcher.cmx.openPage(UrlLocationBuilder.buildUrl(auction.getLocation()));
        dispatcher.cmx.closeDialogIfAppeared();

        newCarList = dispatcher.cmx.saveAllCarsFromElementsWithScroll();

        if (carList == null) carList = newCarList;

        if (carList.size() != newCarList.size()) {
            log.info("New cars were added to the list");
            uniqueCars = CarUtils.getUniqueCars(carList, newCarList);
            log.info("Unique cars: " + uniqueCars.size());

            uniqueCars.forEach(car -> log.info(car.toString()));

            carList.addAll(uniqueCars);
        }

        carList.forEach(car ->
                car.setAuction(auction)
        );

        auction.setCars(Set.copyOf(carList));
        auctionService.update(auction);
    }
}
