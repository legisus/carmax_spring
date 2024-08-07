package scanner.defenitionSteps;

import scanner.scraper.BasePage;
import scanner.scraper.cmx.HomePage;
import scanner.scraper.cmx.PreAuctionPage;
import scanner.scraper.cmx.SigninPage;
import scanner.scraper.hertz.JdpEstimatePage;
import scanner.scraper.manheim.ManheimMainPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import scanner.utils.DiverSetUp;
import utils_api.TreadUtils;

@Slf4j
@Component
public class JdpScraper {
    WebDriver driver;
    HomePage homePage;
    BasePage basePage;
    PreAuctionPage preAuctionPage;
    ManheimMainPage manheimMainPage;
    SigninPage signinPage;
    JdpEstimatePage jdpEstimatePage;

    public JdpScraper() {
        driver = DiverSetUp.getDriver();
        homePage = new HomePage(driver);
        basePage = new BasePage(driver);
        preAuctionPage = new PreAuctionPage(driver);
        manheimMainPage = new ManheimMainPage(driver);
        signinPage = new SigninPage(driver);
        jdpEstimatePage = new JdpEstimatePage(driver);
    }

    public void goToJdpEstimatePage() {
        jdpEstimatePage.openHertzEstimatePage("https://exos.darwinautomotive.com/TradeOnly/Launch/af9e4834-9f5a-4ee4-b1dc-026543bc36ad");
        TreadUtils.sleep(5000);
    }

    public void switchIframe() {
        TreadUtils.sleep(10000);
        jdpEstimatePage.switchToIframe();
    }

    public void switchIframeDefault() {
        TreadUtils.sleep(10000);
        jdpEstimatePage.switchToIframeDefault();
    }

    public String enterJdpEstimateData(String vin, String mileage) {
        TreadUtils.sleep(5000);
        jdpEstimatePage.enterVin(vin);

        jdpEstimatePage.clickLookupVinButton();
        TreadUtils.sleep(5000);

        try {
            jdpEstimatePage.clickNextButton();
            jdpEstimatePage.waitForAjaxToComplete(3000);

            jdpEstimatePage.enterVehicleMileage(mileage);
            jdpEstimatePage.enterVehicleZipcode("90504");
            jdpEstimatePage.clickNextButton();
            jdpEstimatePage.waitForAjaxToComplete(3000);

            jdpEstimatePage.clickSkipVehicleOptionsButton();
            jdpEstimatePage.waitForAjaxToComplete(3000);

            jdpEstimatePage.clickVehicleDefectsNo();
            jdpEstimatePage.clickVehicleSmokeNo();
            jdpEstimatePage.clickVehicleKeys2();
            jdpEstimatePage.clickVehicleLeasedNo();
            jdpEstimatePage.clickVehicleLoanNo();
            jdpEstimatePage.clickNextButton();
            jdpEstimatePage.waitForAjaxToComplete(3000);


            String stingValue = jdpEstimatePage.getRoughValue();
            log.info("The rough value is: {} for vin: {}", stingValue, vin);

            jdpEstimatePage.clickBackButton();
            jdpEstimatePage.waitForAjaxToComplete(1000);
            jdpEstimatePage.clickBackButton();
            jdpEstimatePage.waitForAjaxToComplete(1000);
            jdpEstimatePage.clickBackButton();
            jdpEstimatePage.waitForAjaxToComplete(1000);
            jdpEstimatePage.clickBackButton();
            jdpEstimatePage.waitForAjaxToComplete(1000);

            return stingValue;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("The rough value is $1: {} will be stored", vin);
            return "1";
        }
    }
}
