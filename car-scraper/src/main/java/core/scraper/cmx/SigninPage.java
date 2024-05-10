package core.scraper.cmx;

import core.scraper.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 3. new page, Enter login that stored in property file with name carmax.properties with path C:\Users\mykola.bielousov on PC, xPath: /html/body/div[2]/div/div/div[1]/form/div[5]/ul/li[1]/div/input
 *
 * and password that saved in the same file with xPath: //*[@id="password"]
 */
@Slf4j
public class SigninPage extends BasePage {

    public SigninPage(WebDriver webDriver) {
        super(webDriver);
    }

   @FindBy(xpath = "//input[@id='signInName']")
    private WebElement loginInput;

    @FindBy(xpath="//input[@id='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//hzn-button[@id='continue']")
    private WebElement signInButton;

    public void enterLogin(String login) {
        waitForElementToBeClickable(5, loginInput);
        loginInput.sendKeys(login);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickSignInButton() {
        signInButton.click();
    }
}

