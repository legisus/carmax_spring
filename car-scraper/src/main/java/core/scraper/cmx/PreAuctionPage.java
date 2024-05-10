package core.scraper.cmx;

import core.scraper.BasePage;
import core.utils.CarParser;
import core.model.Car;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils_api.TreadUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PreAuctionPage extends BasePage {
    public PreAuctionPage(WebDriver webDriver) {
        super(webDriver);
    }

    protected JavascriptExecutor js = (JavascriptExecutor) driver;

    @FindBy(xpath = "//p[@class='search-bar-title-iiXeY']/span")
    private WebElement countCarsInAuction;

    @FindBy(xpath = "//div[@role='group']//button[@aria-label='Detailed table View']")
    private WebElement listViewButton;

    @FindBy(xpath = "//button[contains(@class, 'MuiButton-root') and contains(@class, 'MuiButton-outlined') and contains(@class, 'MuiButton-outlinedPrimary') and contains(text(), 'SEE MORE MATCHES')]")
    private WebElement seeMoreMatches;


    public WebElement getCarByIndex(String index) {
        try {
            return driver.findElement(By.xpath("//*[@data-testid='vehicle-table']//div[@data-index='" + index + "']"));
        } catch (Exception e) {
            log.error("Element not found with index: " + index);
            return null;
        }
    }


    public String getCarsInAuction() {
        return countCarsInAuction.getText();
    }

    public void clickOnSeeMoreButton() {
        try {
            seeMoreMatches.click();
        } catch (Exception e) {
            log.info("See more matches button not found");
        }
    }

    public List<Car> saveAllCarsFromWebElementsWithScroll() {
        List<Car> carsTable = new ArrayList<>();
        String numberAsString = getCarsInAuction().replaceAll("[^\\d]", ""); // Corrected to remove all non-digit characters
        int totalCarsWithAdd = Integer.parseInt(numberAsString);
        int countOfWebElementNull = 0;

        for (int i = 0; i < totalCarsWithAdd; i++) {
            log.info("IndexWebElement: " + i + " TotalCarsWithAdd: " + totalCarsWithAdd);

            WebElement webElement = getCarByIndex(String.valueOf(i));

            if (webElement != null) {
                Car car = CarParser.parseCarFromHtml(webElement.getAttribute("outerHTML"));
                countOfWebElementNull = 0;
                if (car != null) {
                    carsTable.add(car);
                    log.info("Cars founded: " + carsTable.size());
                } else {
                    log.info("Add founded");
                    totalCarsWithAdd++;
                    log.info("TotalCarsWithAdd:" + totalCarsWithAdd);
                }
            } else {
                scrollDownByPercentage(1, js); // Scroll down if no element was found
                TreadUtils.sleep(1000);
                i = i - 1;
                if (countOfWebElementNull >= 10) {
                    clickOnSeeMoreButton();
                    TreadUtils.sleep(7000);
                }
                countOfWebElementNull++;
                log.info("Counter of null web elements: " + countOfWebElementNull);
            }
        }
        return carsTable;


//        for (int j = 0; j < 10; j++) {
//            scrollDownByPercentage(100, js);
//            TreadUtils.sleep(1000);
//            clickOnSeeMoreButton();
//        }

//        scrollUpByPercentage(100, js);

//        for (int i = 0; i < totalCarsWithAdd; i++) {
//            log.info();("Iteration: " + i + " Count: " + totalCarsWithAdd);
//            WebElement carElement = getCarByIndex(String.valueOf(i));
//            if (carElement != null) {
//                Car car = CarParser.parseCarFromHtml(carElement.getAttribute("outerHTML"));
//                if (car != null) {
//                    carsTable.add(car);
//                } else {
//                    totalCarsWithAdd++;
//                    log.info();("TotalCars:" + totalCarsWithAdd);
//                }
//            } else {
//                scrollDownByPercentage(2, js); // Scroll down if no element was found
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

    }

    public void closeDialog() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.close-button-BcOSV")));

            // Click the close button
            closeButton.click();

        } catch (Exception e) {
            log.info(e.getMessage());

        }
    }

    public void setListViewButton() {
        listViewButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCarByIndex(int index) {
        try {
            return driver.findElement(By.xpath("//div[@data-index='" + index + "']//div[contains(@class, 'vehicle-vin')]")).getText();
        } catch (Exception e) {
            log.error("Element not found with index: " + index);
            log.info("Element not found with index: " + index);
            return null;
        }
    }

    public String openCarByIndexReturnVin(int index) {
        if (getCarByIndex(index) != null) return getCarByIndex(index);

        scrollDownByPercentage(5, js); // Scroll down if no element was found

        if (getCarByIndex(index) != null) return getCarByIndex(index);

        scrollDownByPercentage(5, js); // Scroll down if no element was found

        TreadUtils.sleep(1000);

        if (getCarByIndex(index) != null) return getCarByIndex(index);

        clickOnSeeMoreButton();

        TreadUtils.sleep(7000);

        if (getCarByIndex(index) != null) return getCarByIndex(index);

        return null;

    }


    public void addNotes(int index, String notes) {
        try {
            WebElement note = driver.findElement(By.xpath("//div[@data-index='" + index + "']//div[contains(@class,'vehicle-note')]/div"));
            note.click();

            //realize kyboard input
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL) // Presses the Control key down.
                    .sendKeys("a") // Sends the 'a' key to select all text.
                    .keyUp(Keys.CONTROL) // Releases the Control key.
                    .sendKeys(Keys.DELETE) // Sends the Delete key to delete the selected text.
                    .sendKeys(notes) // Types the new text into the element.
                    .perform();

            WebElement favorite = driver.findElement(By.xpath("//div[@data-index='" + index + "']//button[contains(@class, 'MuiIconButton-root') and contains(@aria-label, 'favorite')]"));
            favorite.click();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.info("sleep error in click on favorites");
            }

            favorite.click();

        } catch (Exception e) {
            e.getMessage();
            log.info("Element not found with index: " + index);
        }
    }
}
