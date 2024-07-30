package scanner.scraper.manheim;

import scanner.scraper.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MmrPage extends BasePage {


    public MmrPage(WebDriver webDriver) {
        super(webDriver);
    }

    //div[contains(@class, 'mui-col mui-col-1-1 mui-col-1-2-md')]//div[contains(@class, 'styles__currency')/div[1]]
    @FindBy(xpath = "//div[contains(@class, 'mui-col mui-col-1-1 mui-col-1-2-md')]//div[contains(@class, 'styles__currency__EkR32')]")
    private WebElement mmrValue;

    @FindBy(xpath = "//div[contains(@class, 'styles__tableContainer__At0ta')]//table//tr[1]/td[1]")
    private WebElement firstCarInPopupWithCars;

//    @FindBy(xpath = "//div[contains(@class, 'estimatedRetailValue')]//span[contains(@class, 'show')]")
    @FindBy(xpath = "//div[contains(@class, 'estimatedRetailValue')]//span[contains(@class, 'print--hide')]")
    private WebElement estimatedRetailValue;

    public String getMmrValue() {
//        waitForElementNotToBeCondition(5000, mmrValue, "--");
        return mmrValue.getText();
    }

    public String getEstimatedRetailValue() {
        return estimatedRetailValue.getText();
    }

    public void clickOnFirstCarInPopupWithCars() {
        firstCarInPopupWithCars.click();
    }


}
