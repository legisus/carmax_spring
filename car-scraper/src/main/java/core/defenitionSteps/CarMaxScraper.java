package core.defenitionSteps;

import core.credentials.CarMaxAuctionCredentialsUtils;
import core.scraper.BasePage;
import core.scraper.cmx.HomePage;
import core.scraper.cmx.PreAuctionPage;
import core.scraper.cmx.SigninPage;
import core.scraper.cmx.SimulcastPage;
import core.utils.ConvertorUtils;
import core.utils.DiverSetUp;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils_api.CarUtils;
import utils_api.TreadUtils;
import core.model.Car;

import java.util.List;

@Slf4j
public class CarMaxScraper {

    CarMaxAuctionCredentialsUtils carMaxAuctionCredentialsUtils;
    WebDriver driver;
    HomePage homePage;
    BasePage basePage;
    PreAuctionPage preAuctionPage;
    SigninPage signinPage;
    SimulcastPage simulcastPage;
    private String username;
    private String password;

    public CarMaxScraper() {
        carMaxAuctionCredentialsUtils = new CarMaxAuctionCredentialsUtils();

        username = carMaxAuctionCredentialsUtils.getUsername();
        password = carMaxAuctionCredentialsUtils.getPassword();

        driver = DiverSetUp.getDriver();

        homePage = new HomePage(driver);
        basePage = new BasePage(driver);
        preAuctionPage = new PreAuctionPage(driver);
        signinPage = new SigninPage(driver);
        simulcastPage = new SimulcastPage(driver);
    }

    public void openCarMaxActionPage() {
        homePage.openPageByUrl("https://www.carmaxauctions.com/");
    }

    public void signInCarMax() {
        basePage.waitForPageLoadComplete(3000);
        homePage.clickSignInButton();
        signinPage.waitForPageLoadComplete(3000);
        signinPage.enterLogin(username);
        signinPage.enterPassword(password);
        signinPage.clickSignInButton();
        TreadUtils.sleep(5000);
    }

    public void goToCarMaxAuctions() {
        TreadUtils.sleep(5000);
        homePage.clickMemberDashboard();
    }

    public void signOutCarMax() {
//        homePage.scrollUpByPercentage(100, homePage.getJs());
        TreadUtils.sleep(5000);
        homePage.clickSignOutButton();
    }

    public void goToAvailableAuction(String city) {
        homePage.clickAvailableAuction(city);
    }

    public void closeDialogIfAppeared() {
        preAuctionPage.closeDialog();
    }

    public List<Car> saveAllCarsFromElementsWithScroll() {
        TreadUtils.sleep(7000);
        return preAuctionPage.saveAllCarsFromWebElementsWithScroll();
    }


    public void setListView() {
        preAuctionPage.setListViewButton();

    }


    //    public void saveNotesWithEstimation(List<Car> listCars) {
//
//        int nullVinCount = 0;
//        String vin;
//        int countNullVinOcurrance = 0;
//        boolean flag = true;
//
//        while (flag) {
//            vin = preAuctionPage.openCarByIndexReturnVin(nullVinCount);
//
//            if (vin != null) {
//                Optional<Car> foundCar = CarUtils.findCarByVin(listCars, vin);
//                if (foundCar.isPresent()) {
//                    log.info();("Car found: " + foundCar.get());
//                    Optional<Integer> estimationJDPower =
//                            Optional.ofNullable(foundCar.get().getEstimation().getEstimationJDPower());
//                    Optional<Integer> estimationKBBPrivateParty =
//                            Optional.ofNullable(foundCar.get().getEstimation().getEstimationKBBPrivateParty());
//                    Optional<Integer> estimationKBBDealerRetail =
//                            Optional.ofNullable(foundCar.get().getEstimation().getEstimationKBBDealerRetail());
//                    Optional<Integer> estimationManheimMMR =
//                            Optional.ofNullable(foundCar.get().getEstimation().getEstimationManheimMMR());
//
//                    StringBuilder sb = new StringBuilder();
//
//
//                    if (estimationJDPower.isPresent()) {
//                        if (estimationJDPower.get() > 0) {
//                            sb.append("JDP: $").append(estimationJDPower.get()).append("\n");
//                        }
//                    }
//
//                    if (estimationKBBPrivateParty.isPresent()) {
//                        if (estimationKBBPrivateParty.get() > 0) {
//                            sb.append("KBBPP: $").append(estimationKBBPrivateParty.get()).append("\n");
//                        }
//                    }
//
//                    if (estimationKBBDealerRetail.isPresent()) {
//                        if (estimationKBBDealerRetail.get() > 0) {
//                            sb.append("KBBDR: $").append(estimationKBBDealerRetail.get()).append("\n");
//                        }
//                    }
//
//                    if (estimationManheimMMR.isPresent()) {
//                        if (estimationManheimMMR.get() > 0) {
//                            sb.append("MMR: $").append(estimationManheimMMR.get()).append("\n");
//                        }
//                    }
//
//
//                    preAuctionPage.addNotes(nullVinCount, sb + " odo:" + foundCar.get().getMileage());
//                    nullVinCount++;
//
//                } else {
//                    log.info();("No car found with VIN: " + vin);
//                    preAuctionPage.addNotes(nullVinCount, "NotProcessed");
//                    nullVinCount++;
//                }
//            } else {
//                countNullVinOcurrance++;
//                log.info();("Null vin occurance: " + countNullVinOcurrance);
//                nullVinCount++;
//                if (countNullVinOcurrance > 10) flag = false;
//            }
//        }
//
//        log.info();("End of the loop");
//    }
    public void saveNotesWithEstimation(List<Car> listCars) {
        final int maxNullVinOccurrences = 10;
        int nullVinCount = 0;
        int index = 0;

        while (nullVinCount <= maxNullVinOccurrences) {
            String vin = preAuctionPage.openCarByIndexReturnVin(index);
            if (vin == null) {
                nullVinCount++;
                log.info("Null vin occurrence: " + nullVinCount);
            } else {
                nullVinCount = 0; // Reset the count for consecutive non-null VINs
                final Car car = CarUtils.findCarByVin(listCars, vin)
                        .orElse(null);

                if (car != null) {
                    log.info("Car found: " + car);
                    preAuctionPage.addNotes(index, buildCarNotes(car));
                } else {
                    log.info("No car found with VIN: " + vin);
                    preAuctionPage.addNotes(index, "NotProcessed");
                }
            }
            index++; // Increment index here to ensure it's effectively final within any lambda
        }

        log.info("End of the loop");
    }

    private String buildCarNotes(Car car) {
        StringBuilder sb = new StringBuilder();
        String defects = car.getDefects();

        if(defects != null && !defects.isEmpty()) {
//            if(!defects.contains("Announcement") && car.getEstimation().getEstimationManheimMMR() < 10000 && car.getEstimation().getEstimationManheimMMR() > 1000) {
//                sb.append("Deal! \n");
//            }

        }

//        sb.append("Est: $").append(getEstimationRange(car)).append("\n");
        sb.append("Est: $").append(getEstimationRange(car)).append("\n")
                .append("Ret: $").append(ConvertorUtils.getEstimationStringK(car.getEstimation().getEstimatedRetailValue()))
                .append("\n");

        if(car.getMileage() < 100000 && car.getYear() > 2012) {
            if(defects.contains("Engine") || defects.contains("Transmission") || defects.contains("Runner")
                    || defects.contains("Key") || defects.contains("Keys")) {
                sb.append(" !Tur:").append(ConvertorUtils.getEstimationStringK(car.getMileage())).append("k");
            }else {
                sb.append(" Tur:").append(ConvertorUtils.getEstimationStringK(car.getMileage())).append("k");
            }
        }else {
            sb.append(" odo:").append(ConvertorUtils.getEstimationStringK(car.getMileage())).append("k");
        }
        return sb.toString();
    }

//    private void addEstimationNote(StringBuilder sb, String prefix, Integer estimation) {
//        if (estimation != null && estimation > 0) {
//            sb.append(prefix).append(": $").append(estimation).append("\n");
//        }
//    }

    private String getEstimationRange(Car car) {
        int min = 0;
        int max = 0;
        if (car.getEstimation().getEstimationJDPower() != null) {
            min = car.getEstimation().getEstimationJDPower();
            max = car.getEstimation().getEstimationJDPower();
        }
        if (car.getEstimation().getEstimationKBBPrivateParty() != null) {
            if (min > car.getEstimation().getEstimationKBBPrivateParty()) {
                min = car.getEstimation().getEstimationKBBPrivateParty();
            }
            if (max < car.getEstimation().getEstimationKBBPrivateParty()) {
                max = car.getEstimation().getEstimationKBBPrivateParty();
            }
        }
        if (car.getEstimation().getEstimationKBBDealerRetail() != null) {
            if (min > car.getEstimation().getEstimationKBBDealerRetail()) {
                min = car.getEstimation().getEstimationKBBDealerRetail();
            }
            if (max < car.getEstimation().getEstimationKBBDealerRetail()) {
                max = car.getEstimation().getEstimationKBBDealerRetail();
            }
        }
        if (car.getEstimation().getEstimationManheimMMR() != null) {
            if (min > car.getEstimation().getEstimationManheimMMR()) {
                min = car.getEstimation().getEstimationManheimMMR();
            }
            if (max < car.getEstimation().getEstimationManheimMMR()) {
                max = car.getEstimation().getEstimationManheimMMR();
            }
        }
        return ConvertorUtils.getEstimationStringK(min) + "-" + ConvertorUtils.getEstimationStringK(max) + "k";
    }


    public void close() {
        TreadUtils.sleep(3000);
        driver.quit();
    }

    public List<WebElement> scrapeSimulcast() {
        openCarMaxActionPage();
        TreadUtils.sleep(7000);
        signInCarMax();
        TreadUtils.sleep(7000);
        homePage.clickSimulcastLink();
        TreadUtils.sleep(7000);
//        simulcastPage.activateAllAuctions();
//        TreadUtils.sleep(5000);
        simulcastPage.clickAcknowledgeCheckbox();
        TreadUtils.sleep(500);
        simulcastPage.clickCheckInButton();
        TreadUtils.sleep( 60000);
        simulcastPage.activateAllLines();
        TreadUtils.sleep(5000);
        return simulcastPage.getLanePanelList();
    }
}
