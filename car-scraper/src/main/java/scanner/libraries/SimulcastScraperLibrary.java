package scanner.libraries;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class SimulcastScraperLibrary {

    @Getter
    @Setter
    List<WebElement> listOfAuctionsInfo;

    public String convertWebElementToString(WebElement webElement) {
        return webElement.getAttribute("outerHTML");
    }

    public void writeListOfAuctionsInfoToFile(List<WebElement> listOfAuctionsInfo, String path) {
        BufferedWriter writer = null;
        try {
            log.info("Create a BufferedWriter instance with a FileWriter");
            writer = new BufferedWriter(new FileWriter(path));

            log.info("Iterate over the list of WebElements");
            for (WebElement element : listOfAuctionsInfo) {
                log.info("Convert the WebElement to a string");
                String htmlContent = convertWebElementToString(element);

                log.info(" Write the string to the file using the BufferedWriter");
                writer.write(htmlContent);

                log.info("Write a new line after each element's content for separation");
                writer.newLine();
            }
        } catch (IOException e) {
            log.info("Handle possible IOException from FileWriter or BufferedWriter");
            log.error(e.getMessage());
        } finally {
            log.info("Attempt to close the BufferedWriter if it's not null");
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.info("Handle a possible exception during close");
                    log.error(e.getMessage());
                }
            }
        }
    }


}
