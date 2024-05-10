package core.dispetchers;

import core.defenitionSteps.CarMaxScraper;
import core.defenitionSteps.JdpScraper;
import core.defenitionSteps.MainheimScraper;
import core.model.Auction;
import core.utils.InitCarListFile;
import core.model.Car;
import core.model.Estimation;
import core.model.enums.Locations;
import lombok.extern.slf4j.Slf4j;
import utils_api.CarUtils;
import utils_api.ConstantUtils;
import utils_api.TreadUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DispatcherCombined {


    protected CarMaxScraper cmx;
    protected JdpScraper jdp;
    protected MainheimScraper mmr;



    public DispatcherCombined(CarMaxScraper cmx, JdpScraper jdp, MainheimScraper mmr) {
        this.cmx = cmx;
        this.jdp = jdp;
        this.mmr = mmr;
    }

    static LocalDate localDateOfAuction = LocalDate.of(2024, 5, 14);

//    static String location = Location.MURRIETA.getLocation();
//    static String location = Location.OCEANSIDE.getLocation();
//    static String location = Location.DUARTE.getLocation();
//    static String location = Location.IRVINE.getLocation();
//    static String location = Location.OXNARD.getLocation();

//    static String location = Locations.MURRIETA.getLocation();
    static String location = Locations.DES_MOINES.getLocation();

//    protected Auction auction = new Auction();
//    auction.setDateTimeOfAuction(localDateOfAuction);


    static String path = "C:/props/objects/" + localDateOfAuction + "_" + location + "_carList.properties";

    static List<Car> carList;
    static List<Car> newCarList;
    static List<Car> uniqueCars;


    private static void updateMmrAndRetailEstimation(Car car, MainheimScraper mmr) {

        try {
            if (car.getEstimation() == null) {
                car.setEstimation(new Estimation());
                log.info("Estimation is null, and initialized");
            }
        } catch (Exception e) {
            log.error(ConstantUtils.ERROR_MESSAGE + e.getMessage());
        }

        try {
            String vin = car.getVin();
            String millageString = String.valueOf(car.getMileage());
            String[] mmrAndRetailArr = mmr.sendMmrAndRetailQueryUrl(vin, millageString);
//            String mmrEstimate = mmr.sendMmrAndRetailQueryUrl(vin, millageString);
            log.info("VIN: " + vin + " MMR: " + mmrAndRetailArr[0] + " Retail: " + mmrAndRetailArr[1]);

            car.getEstimation().setEstimationManheimMMR(Integer.parseInt(mmrAndRetailArr[0].replaceAll("[^\\d]", "")));

            int retailPrice = Integer.parseInt(mmrAndRetailArr[1].replaceAll("[^\\d]", ""));
            int correctedRetailPrice = (int) (retailPrice * 0.8);
            log.info("Corrected Retail Price: " + correctedRetailPrice);
            car.getEstimation().setEstimatedRetailValue(correctedRetailPrice);

        } catch (Exception e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
                log.info(ConstantUtils.SERIALIZATION_FROM_CATCH);
                CarUtils.saveCarsListToJsonFile(carList, path);
                log.info(ConstantUtils.SUCCESS_MESSAGE);
            }
            log.error(ConstantUtils.ERROR_VIN + e.getMessage());

            car.getEstimation().setEstimationManheimMMR(-1);
            car.getEstimation().setEstimatedRetailValue(-1);
        }
    }

    private static void updateJDPowerEstimation(Car car, JdpScraper jdp) {

        try {
            if (car.getEstimation() == null) {
                log.info("Initialize Estimation if it doesn't exist");
                car.setEstimation(new Estimation());
            }
        } catch (Exception e) {
            log.error(ConstantUtils.ERROR_VIN + e.getMessage());
        }

        try {
            String vin = car.getVin();
            String millageString = String.valueOf(car.getMileage());
            String hertzEstimate = jdp.enterJdpEstimateData(vin, millageString);
            car.getEstimation().setEstimationJDPower(Integer.parseInt(hertzEstimate.replaceAll("[^\\d]", "")));
        } catch (Exception e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                log.error(ex.getMessage());
                log.info(ConstantUtils.SERIALIZATION_FROM_CATCH);
                CarUtils.saveCarsListToJsonFile(carList, path);
                log.info(ConstantUtils.SUCCESS_MESSAGE);
            }
            log.info("Error VIN: " + e.getMessage());
            car.getEstimation().setEstimationJDPower(-1);
        }
    }

    public static void main(String[] args) {

        DispatcherCombined dispatcher = new DispatcherCombined(new CarMaxScraper(), new JdpScraper(), new MainheimScraper());

        log.info(ConstantUtils.DESERIALIZE_MESSAGE);
        carList = InitCarListFile.initCarList(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);

//        //*************************1.CARMAX***************************

        dispatcher.cmx.openCarMaxActionPage();
        dispatcher.cmx.signInCarMax();
        dispatcher.cmx.goToCarMaxAuctions();
        dispatcher.cmx.goToAvailableAuction(location);
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


        //todo add location to carList
//        carList.forEach(car -> {
//
//            car.setAuction(location);
//
//            car.setLocation(location);
//            car.setDateOfAuction(localDateOfAuction.toString());
//        });


        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveCarsListToJsonFile(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);

////        **************************2.MMR**************************

        dispatcher.mmr.openManheimLoginPage();
        TreadUtils.sleep(7000);
        dispatcher.mmr.signInManheim();

        try {
            for (Car car : carList) {
                if (car.getEstimation() == null) {
                    log.info("in if null");
                    updateMmrAndRetailEstimation(car, dispatcher.mmr);
                }

                if (car.getEstimation().getEstimationManheimMMR() == null) {
                    log.info("in if getEstimationManheimMMR() == null");
                    updateMmrAndRetailEstimation(car, dispatcher.mmr);
                }

                if (car.getEstimation().getEstimationManheimMMR() < 1) {
                    log.info("in if < 1");
                    updateMmrAndRetailEstimation(car, dispatcher.mmr);
                }
            }
        } catch (Exception e) {
            log.info("Error: " + e.getMessage());
            log.info(ConstantUtils.SERIALIZATION_FROM_CATCH);
            CarUtils.saveCarsListToJsonFile(carList, path);
            log.info(ConstantUtils.SUCCESS_MESSAGE);
        }


        for (Car car : carList) {
            Integer mmr = Optional.ofNullable(car.getEstimation())
                    .map(Estimation::getEstimationManheimMMR)
                    .orElse(0); // Default to 0 if null
            Integer retail = Optional.ofNullable(car.getEstimation())
                    .map(Estimation::getEstimatedRetailValue)
                    .orElse(0); // Default to 0 if null

            String info = car.getVin() + " MMR: $" + mmr + " Retail: $" + retail;
            log.info(info);


//            String info = car.getVin() + " MMR: $" + car.getEstimation().getEstimationManheimMMR();
//            log.info(info);
        }

        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveCarsListToJsonFile(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);

//////                **************************3.J.D.Power **************************


        dispatcher.jdp.goToJdpEstimatePage();
        dispatcher.jdp.switchIframe();

        try {
            for (Car car : carList) {
                if (car.getEstimation() == null) {
                    log.info("in if null");
                    updateJDPowerEstimation(car, dispatcher.jdp);
                }

                if (car.getEstimation().getEstimationJDPower() == null) {
                    log.info("in if getEstimationJDPower() == null");
                    updateJDPowerEstimation(car, dispatcher.jdp);
                }

                if (car.getEstimation().getEstimationJDPower() < 1) {
                    log.info("in if < 1");
                    updateJDPowerEstimation(car, dispatcher.jdp);
                }

                if (car.getEstimation().getEstimationJDPower() > 0) {
                    log.info("No Updates Needed");
                }
            }
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
            log.info(ConstantUtils.SERIALIZATION_FROM_CATCH);
            CarUtils.saveCarsListToJsonFile(carList, path);
            log.info(ConstantUtils.SUCCESS_MESSAGE);
        }


        for (Car car : carList) {
            Integer mmr = Optional.ofNullable(car.getEstimation())
                    .map(Estimation::getEstimationJDPower)
                    .orElse(0); // Default to 0 if null
            String info = car.getVin() + " JDP: $" + mmr;
            log.info(info);
        }

        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveCarsListToJsonFile(carList, path);
        log.info(ConstantUtils.SUCCESS_MESSAGE);


        //**************************4.CARMAX SAVE OR UPDATE**************************

        dispatcher.cmx.openCarMaxActionPage();
        TreadUtils.sleep(7000);
//        dispatcher.cmx.signInCarMax();
        dispatcher.cmx.goToCarMaxAuctions();
        dispatcher.cmx.goToAvailableAuction(location);
        dispatcher.cmx.closeDialogIfAppeared();
        dispatcher.cmx.setListView();
        dispatcher.cmx.saveNotesWithEstimation(carList);
        dispatcher.cmx.signOutCarMax();
        dispatcher.cmx.close();
    }
}
