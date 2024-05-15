package scanner.scraper.hertz;

import scanner.scraper.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class JdpEstimatePage extends BasePage {
    public JdpEstimatePage(WebDriver webDriver) {
        super(webDriver);
    }

    public void openHertzEstimatePage(String url) {
        driver.get(url);
    }


    @FindBy(xpath = "//div[@class='ddc-wrapper']//div[@class='main']//div[@class='ddc-wrapper']//div[@class='page-bd']//div[@id='online-form'")
    private WebElement ddcWrapper;


    @FindBy(xpath = "//input[@id='VIN' and contains(@class, 'form-control')]")
    private WebElement vinInput;


    @FindBy(xpath = "//button[@id='LookupVinButton']")
    private WebElement lookupVinButton;

    @FindBy(xpath = "//footer[@id='layout-footer']//li[contains(@class, 'next')]/button[contains(@class, 'trackga4') and contains(@class, 'exos-btn-blue')]")
    private WebElement nextButton;

    @FindBy(xpath = "//footer[@id='layout-footer']//button[contains(@class, 'trackga4') and contains(@class, 'btn') and contains(@class, 'exos-btn') and contains(@class, 'exos-btn-darkgrey')]")
    private WebElement backButton;


    @FindBy(xpath = "//input[@id='VehicleMileage']")
    private WebElement vehicleMileage;

    @FindBy(xpath = "//input[@id='VehicleZipcode']")
    private WebElement vehicleZipcode;

    @FindBy(xpath = "//button[@id='btnSkipVehicleOptions']")
    private WebElement skipVehicleOptionsButton;

    @FindBy(xpath = "//label[contains(., 'No') and .//input[@id='VehicleDefectsNo']]")
    private WebElement vehicleDefectsNo;

    @FindBy(xpath = "//label[contains(., 'No') and .//input[@id='VehicleSmokeNo']]")
    private WebElement vehicleSmokeNo;

    @FindBy(xpath = "//label[contains(., '2+') and .//input[@id='VehicleKeys2']]")
    private WebElement vehicleKeys2;

    @FindBy(xpath = "//label[contains(., 'No') and .//input[@id='VehicleLeasedNo']]")
    private WebElement vehicleLeasedNo;

    @FindBy(xpath = "//label[contains(., 'No') and .//input[@id='VehicleLoanNo']]")
    private WebElement vehicleLoanNo;

    @FindBy(xpath = "//span[@id='RoughValue' and contains(@class, 'endTradeValue')]")
    private WebElement roughValue;

    @FindBy(css = "#myTradeOnlyForm_iframe>iframe")
    private WebElement iframe;

    public void switchToIframe() {
        driver.switchTo().frame("myTradeOnlyForm_iframe");
    }

    public void switchToIframeDefault() {
        driver.switchTo().defaultContent();
    }

    public void enterVin(String vin) {
        vinInput.clear();
        vinInput.sendKeys(vin);
    }

    public void clickLookupVinButton() {
        lookupVinButton.click();




    }

    public void clickNextButton() {
        nextButton.click();
    }

    public void enterVehicleMileage(String mileage) {
        vehicleMileage.clear();
        vehicleMileage.sendKeys(mileage);
    }

    public void enterVehicleZipcode(String zipcode) {
        vehicleZipcode.clear();
        vehicleZipcode.sendKeys(zipcode);
    }

    public void clickSkipVehicleOptionsButton() {
        skipVehicleOptionsButton.click();
    }

    public void clickVehicleDefectsNo() {
        vehicleDefectsNo.click();
    }

    public void clickVehicleSmokeNo() {
        vehicleSmokeNo.click();
    }

    public void clickVehicleKeys2() {
        vehicleKeys2.click();
    }

    public void clickVehicleLeasedNo() {
        vehicleLeasedNo.click();
    }

    public void clickVehicleLoanNo() {
        vehicleLoanNo.click();
    }

    public String getRoughValue() {
        return roughValue.getText();
    }

    public void clickBackButton() {
        backButton.click();
    }


}
