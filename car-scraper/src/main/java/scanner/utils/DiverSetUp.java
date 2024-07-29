package scanner.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

@Slf4j
public class DiverSetUp {
    @Getter
    private static final WebDriver driver;

    static {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-infobars");
        options.addArguments("start-maximized");
//        options.addArguments("incognito");

        driver = new ChromeDriver(options);
    }
}
