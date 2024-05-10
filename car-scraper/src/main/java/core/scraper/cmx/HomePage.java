package core.scraper.cmx;

import core.scraper.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils_api.TreadUtils;

@Slf4j
public class HomePage extends BasePage {

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

//    @Getter
//    private JavascriptExecutor js = (JavascriptExecutor) driver;

    @FindBy(xpath = "//hzn-button[text()='sign in']")
    private WebElement signInButton;

    public void clickSignInButton() {
        waitForElementToBeClickable(2, signInButton);
        signInButton.click();
    }

    @FindBy(xpath = "//button[@class='MuiButton-root MuiButton-text MuiButton-textPrimary MuiButton-sizeMedium MuiButton-textSizeMedium MuiButton-disableElevation MuiButtonBase-root  css-bkt4x5']")
    private WebElement memberDashboard;

    @FindBy(xpath = "//div[@role='tooltip']//a[text()='My Auctions']")
    private WebElement myAuctions;

    @FindBy(xpath = "//div[@role='tooltip']//a[text()='Sign Out']")
    private WebElement signOutButton;

//    @FindBy(xpath = "//tr[@class='gridrow']//td[contains(text(),'+" city "+')]/following-sibling::td/a[contains(text(),'Available')]")
//    private WebElement availableAuction;

    @FindBy(xpath = "//*[@id='simulcast-link']")
    private WebElement simulcastLink;

    @FindBy(xpath = "//a[contains(text(),'Sign out')]")
    private WebElement signOutInMainMenu;


    public void clickSimulcastLink() {
        simulcastLink.click();
    }

    public WebElement getAvailableAuctionElement(String city) {
        String xpathExpression = String.format("//tr[@class='gridrow']//td[contains(text(),'%s')]/following-sibling::td/a[contains(text(),'Available')]", city);
        return driver.findElement(By.xpath(xpathExpression));
    }

    public void clickMemberDashboard() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');" +
                "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} " +
                "else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        js.executeScript(mouseOverScript, memberDashboard);
        js.executeScript("arguments[0].click();", myAuctions);
    }

    public void clickAvailableAuction(String city) {
        waitForPageLoadComplete(5000);
        getAvailableAuctionElement(city).click();
    }

    public void clickSignOutButton() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        scrollUpByPercentage(100, js);

        TreadUtils.sleep(3000);
        clickMemberDashboard();
        TreadUtils.sleep(5000);
        signOutInMainMenu.click();
    }
}
