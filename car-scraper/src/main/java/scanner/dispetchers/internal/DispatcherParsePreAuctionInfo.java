package scanner.dispetchers.internal;

import core.model.Car;
import core.model.enums.Locations;
import lombok.extern.slf4j.Slf4j;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.defenitionSteps.JdpScraper;
import scanner.defenitionSteps.MainheimScraper;
import scanner.utils.InitCarListFile;
import scanner.utils.UrlLocationBuilder;
import utils_api.CarUtils;
import utils_api.ConstantUtils;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class DispatcherParsePreAuctionInfo {

    static List<Car> carList;
    static List<Car> newCarList;
    static List<Car> uniqueCars;

    static String location = Locations.HARTFORD.getLocation();
    static LocalDate localDateOfAuction = LocalDate.of(2024, 7, 24);
    static String path = "/Users/macbook/Documents/" + localDateOfAuction + "_" + location + "_carListPreAuction.properties";



    public static void main(String[] args) {

        DispatcherScriptCombined dispatcher = new DispatcherScriptCombined(new CarMaxScraper(), new JdpScraper(), new MainheimScraper());

        log.info(ConstantUtils.DESERIALIZE_MESSAGE);
        carList = InitCarListFile.initCarList(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);

//        //*************************1.CARMAX***************************

        dispatcher.cmx.openCarMaxActionPage();
        dispatcher.cmx.signInCarMax();
        dispatcher.cmx.openPage(UrlLocationBuilder.buildUrl(Locations.HARTFORD));
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


        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveCarsListToJsonFile(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);
    }
}
