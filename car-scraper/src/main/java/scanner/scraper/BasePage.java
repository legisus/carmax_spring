package scanner.scraper;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
@Slf4j
public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver webDriver){
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void waitForPageLoadComplete(long timeToWait) {
        new WebDriverWait(driver, Duration.ofSeconds(timeToWait)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void waitForAjaxToComplete(long timeToWait) {
        new WebDriverWait(driver, Duration.ofSeconds(timeToWait)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return window.jQuery != undefined && jQuery.active == 0;"));
    }

    public void waitVisibilityOfElement(long timeToWait, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void implicitWaiter(long timeToWait){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeToWait));
    }

    public void scriptWaiter(long timeToWait){
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeToWait));
    }

    public void pageLoadWait(long timeToWait){
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeToWait));
    }

    public void waitForElementToBeClickable(long timeToWait, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementNotToBeCondition(long timeToWait, WebElement element, String condition) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeToWait));
            wait.until(ExpectedConditions.not(x -> !element.getText().equals(condition)));
        }catch (Exception e) {
            log.error("Problem with element in waitForElementNotToBeCondition()");
        }
    }

    public void scrollDownByPercentage(int percentage, JavascriptExecutor js) {
        // Calculate the scroll amount as a percentage of the total document height
        js.executeScript(
                "const documentHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight, document.documentElement.offsetHeight, document.body.clientHeight, document.documentElement.clientHeight);" +
                        "window.scrollBy(0, documentHeight * " + percentage / 100.0 + ");"
        );
    }

    public void scrollUpByPercentage(int percentage, JavascriptExecutor js) {
        // Calculate the scroll amount as a percentage of the total document height
        js.executeScript(
                "const documentHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight, document.body.offsetHeight, document.documentElement.offsetHeight, document.body.clientHeight, document.documentElement.clientHeight);" +
                        "window.scrollBy(0, -documentHeight * " + percentage / 100.0 + ");"
        );
    }

    public void openPageByUrl(String url) {
        try{
            driver.get(url);
            pageLoadWait(7000);
        }catch (Exception e) {
            e.getCause();
        }
    }

}
