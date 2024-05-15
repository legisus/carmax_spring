package scanner.scraper.cmx;

import scanner.scraper.BasePage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils_api.TreadUtils;

import java.util.List;

@Slf4j
public class SimulcastPage extends BasePage {
    public SimulcastPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = "//input[@type='checkbox']")
    private WebElement acknowledgeCheckbox;

    @FindBy(xpath = "//button[text()='CHECK IN']")
    private WebElement checkInButton;

    @FindBy(xpath = "//span[@class='view-lane XXX-lane-col ']")
    private List<WebElement> simulcastLanesStateOffList;

    @Getter
    @FindBy(xpath = "//li[contains(@class, 'lane-column') and contains(@class, 'lane-panel') and contains(@class, 'double-wide')]")
    private List<WebElement> lanePanelList;

    @FindBy(xpath = "//div[@data-auction-id]")
    private List<WebElement> auctionList;

    @FindBy(xpath = "//div[contains(@class, 'MuiAccordionSummary-content')]//p")
    private WebElement dealerNameOnPopUp;

    @FindBy(xpath = "//span[contains(@class, 'MuiFormControlLabel-label') and contains(text(), 'CASH')]")
    private WebElement cashRadioButton;

    @FindBy(xpath = "//button[contains(@class, 'MuiButton-root') and contains(text(), 'Save')]")
    private WebElement saveButton;

    public void clickCheckInButton() {
        checkInButton.click();
    }

    public void clickAcknowledgeCheckbox() {
        acknowledgeCheckbox.click();
    }

    public void activateAllLines() {
        while (!simulcastLanesStateOffList.isEmpty()) {
            log.info("Simulcast lanes state off list size: " + simulcastLanesStateOffList.size());
            simulcastLanesStateOffList.get(0).click();
            TreadUtils.sleep(10000);
        }
    }

    public void activateAllAuctions() {
        if (!auctionList.isEmpty()) {
            for (WebElement auction : auctionList) {
                auction.click();
                TreadUtils.sleep(1000);
                dealerNameOnPopUp.click();
                TreadUtils.sleep(1000);
                cashRadioButton.click();
                TreadUtils.sleep(1000);
                saveButton.click();
                TreadUtils.sleep(1000);
            }
        }
    }
}
