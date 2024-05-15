package scanner.defenitionSteps;

import scanner.credentials.ManheimCredentials;
import scanner.scraper.BasePage;
import scanner.scraper.cmx.HomePage;
import scanner.scraper.cmx.PreAuctionPage;
import scanner.scraper.cmx.SigninPage;
import scanner.scraper.manheim.ManheimMainPage;
import scanner.scraper.manheim.MmrPage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import scanner.utils.DiverSetUp;
import utils_api.TreadUtils;

@Slf4j
@Component
public class MainheimScraper {
    ManheimCredentials manheimCredentials;
    WebDriver driver;
    HomePage homePage;
    BasePage basePage;
    PreAuctionPage preAuctionPage;
    ManheimMainPage manheimMainPage;
    SigninPage signinPage;
    MmrPage mmrPage;
    private String manheimUsername;
    private String manheimPassword;

    public MainheimScraper() {
        manheimCredentials = new ManheimCredentials();
        manheimUsername = manheimCredentials.getUsername();
        manheimPassword = manheimCredentials.getPassword();
        driver = DiverSetUp.getDriver();
        basePage = new BasePage(driver);
        homePage = new HomePage(driver);
        preAuctionPage = new PreAuctionPage(driver);
        manheimMainPage = new ManheimMainPage(driver);
        signinPage = new SigninPage(driver);
        mmrPage = new MmrPage(driver);
    }

    public void openManheimLoginPage() {
        homePage.openPageByUrl("https://members.manheim.com/gateway/login");
    }

    public void signInManheim() {
        basePage.waitForPageLoadComplete(3000);
        manheimMainPage.enterLogin(manheimUsername);
        manheimMainPage.enterPassword(manheimPassword);
        manheimMainPage.clickSignInButton();
        log.info("Entering username");
    }

    public String[] sendMmrAndRetailQueryUrl(String vin, String millage) {
        String urlQuery = "https://mmr.manheim.com/ui-mmr/?country=US&popup=false&source=man&vin=" + vin + "&mileage=" + millage;
        manheimMainPage.openPageByUrl(urlQuery);

        TreadUtils.sleep(5000);

        String[] mmrAndRetailArr = getMmrValue();

        for (int i = 0; i < 5; i++) {
            log.info("For loop iteration: " + i);
            if (mmrAndRetailArr[0] == null || mmrAndRetailArr[1] == null) {
                TreadUtils.sleep(1000);
                log.info("mmrAndRetailArr[0]=" + mmrAndRetailArr[0] + " mmrAndRetailArr[1]=" + mmrAndRetailArr[1]);
                mmrAndRetailArr = getMmrValue();
            }

            if (mmrAndRetailArr[0] != null && mmrAndRetailArr[1] != null) {
                log.info("mmrAndRetailArr[0]=" + mmrAndRetailArr[0] + " mmrAndRetailArr[1]=" + mmrAndRetailArr[1]);
                return mmrAndRetailArr;
            }
        }
        log.info("Mmr value is null in sendMmrAndRetailQueryUrl()");
        return mmrAndRetailArr;

    }

    private String[] getMmrValue() {

        int count = 0;
        boolean flag = true;


        while (flag) {
            try {
                String mmr = mmrPage.getMmrValue();
                log.info("Mmr value: " + mmr);
                String estimatedRetailValue = mmrPage.getEstimatedRetailValue();
                log.info("Estimated Retail Value: " + estimatedRetailValue);


                if (mmr == null) {
                    log.info("Mmr value is null");
                    TreadUtils.sleep(1000);
                }

                if (mmr == null) {
                    log.info("Mmr value is null");
                    TreadUtils.sleep(1000);
                }

                if (!mmr.equals("--")) return new String[]{mmr, estimatedRetailValue};


                if (mmr.equals("")) TreadUtils.sleep(1000);

                try {
                    mmrPage.clickOnFirstCarInPopupWithCars();
                    log.info("Clicked on first car in popup with cars");
                    TreadUtils.sleep(5000);
                } catch (Exception e) {
                    log.info("Error clicking on first car in popup with cars: " + e.getMessage());
                }

                mmr = mmrPage.getMmrValue();
                if (!mmr.equals("--")) {
                    return new String[]{mmr, estimatedRetailValue};
                } else {
                    log.info("Mmr value is still --");
                }

                return new String[]{mmr, estimatedRetailValue};
            } catch (Exception e) {
                log.info("Error getting MMR value: " + e.getMessage());
                count++;
                TreadUtils.sleep(1000);
                if (count > 10) flag = false;
                log.info("Error Count: " + count);
            }
        }
        log.info("Mmr value is null, and null will be returned");
        return new String[0];
    }


}
