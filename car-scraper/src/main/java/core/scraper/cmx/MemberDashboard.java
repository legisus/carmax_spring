package core.scraper.cmx;

import core.scraper.BasePage;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@Slf4j
public class MemberDashboard extends BasePage {

    public MemberDashboard(WebDriver webDriver) {
        super(webDriver);
    }

    //Member Dashboard, click on "available" by xpath: /html/body/div[2]/div[1]/div[4]/div[2]/div/table[1]/tbody/tr[2]/td[5]/a

    @FindBy(xpath = "/html/body/div[2]/div[1]/div[4]/div[2]/div/table[1]/tbody/tr[2]/td[5]/a")
    private WebElement available;

    public void clickAvailable() {
        available.click();
    }

}
