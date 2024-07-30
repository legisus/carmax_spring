package scanner.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import scanner.dto.CarSimuscastDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class HtmlToJsonConverter {

    public void convertFromTxtFileToJsonFile(String inputFile, String outputFile) {
        try {
            log.info("Load the HTML file");
            File input = new File(inputFile);
            Document doc = Jsoup.parse(input, "UTF-8");

            log.info("Extract the required data");
            List<Map<String, Object>> carList = new ArrayList<>();
            Elements rows = doc.select("tr");

            for (Element row : rows) {
                Map<String, Object> carData = new HashMap<>();

                log.info("Extract run number and car description");
                Elements itemInfo = row.select(".item-info-row a.vc-details");
                if (!itemInfo.isEmpty()) {
                    String[] runNumberAndDescription = itemInfo.text().split("-", 2);
                    if (runNumberAndDescription.length == 2) {
                        carData.put("runNumber", runNumberAndDescription[0].trim());
                        carData.put("description", runNumberAndDescription[1].trim());
                    }
                }

                log.info("Extract color and mileage");
                Elements mileageInfo = row.select(".mileage-row");
                if (!mileageInfo.isEmpty()) {
                    String color = mileageInfo.select(".block").text();
                    String mileageText = mileageInfo.text().replace(color, "").replace("M", "").replace(",", "").trim();
                    carData.put("color", color);
                    try {
                        carData.put("mileage", Integer.parseInt(mileageText));
                    } catch (NumberFormatException e) {
                        carData.put("mileage", 0);
                    }
                }

                log.info("Extract status");
                Elements statusInfo = row.select(".badge-row");
                if (!statusInfo.isEmpty()) {
                    carData.put("status", statusInfo.text().trim());
                }

                log.info("Extract price");
                Elements priceInfo = row.select(".price-row span");
                if (!priceInfo.isEmpty()) {
                    String priceText = priceInfo.text().replace("$", "").replace(",", "").trim();
                    try {
                        carData.put("price", Integer.parseInt(priceText));
                    } catch (NumberFormatException e) {
                        carData.put("price", 0);
                    }
                }

                if (!carData.isEmpty()) {
                    carList.add(carData);
                }
            }

            log.info("Convert to JSON using Jackson");
            ObjectMapper mapper = new ObjectMapper();
            String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(carList);

            log.info("Save JSON to file");
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), carList);

        } catch (IOException e) {
            log.info("Error while converting HTML to JSON");
            log.info(String.valueOf(e.getCause()));
        }
    }

    public void convertFromHtmlFileToJsonFilePreAuctionLines(String inputFile, String outputFile) {
        try {
            System.out.println("Loading the HTML file");
            File input = new File(inputFile);
            Document doc = Jsoup.parse(input, "UTF-8");

            System.out.println("Extracting the required data");
            CarSimuscastDto carSimuscastDto = new CarSimuscastDto();

            // Parse run number and description
            Elements itemInfoElements = doc.select(".item-info-row a.vc-details");
            if (!itemInfoElements.isEmpty()) {
                Element itemInfo = itemInfoElements.first();
                System.out.println("Item Info: " + itemInfo.text());
                String[] runNumberAndDescription = itemInfo.text().split("-", 2);
                if (runNumberAndDescription.length == 2) {
                    carSimuscastDto.setRunNumber(runNumberAndDescription[0].trim());
                    carSimuscastDto.setDescription(runNumberAndDescription[1].trim());
                }
            }

            // Parse color and mileage
            Elements mileageInfoElements = doc.select(".mileage-row");
            if (!mileageInfoElements.isEmpty()) {
                Element mileageInfo = mileageInfoElements.first();
                System.out.println("Mileage Info: " + mileageInfo.text());
                String color = mileageInfo.selectFirst(".block").text();
                String mileageText = mileageInfo.text().replace(color, "").replace("M", "").replace(",", "").trim();
                carSimuscastDto.setColor(color);
                try {
                    carSimuscastDto.setMileage(Integer.parseInt(mileageText));
                } catch (NumberFormatException e) {
                    carSimuscastDto.setMileage(0);
                }
            }

            // Parse status
            Elements statusInfoElements = doc.select(".badge-row");
            if (!statusInfoElements.isEmpty()) {
                Element statusInfo = statusInfoElements.first();
                System.out.println("Status Info: " + statusInfo.text());
                carSimuscastDto.setStatus(statusInfo.text().trim());
            }

            // Parse price
            Elements priceInfoElements = doc.select(".price-row span");
            if (!priceInfoElements.isEmpty()) {
                Element priceInfo = priceInfoElements.first();
                System.out.println("Price Info: " + priceInfo.text());
                String priceText = priceInfo.text().replace("$", "").replace(",", "").trim();
                try {
                    carSimuscastDto.setPrice(Integer.parseInt(priceText));
                } catch (NumberFormatException e) {
                    carSimuscastDto.setPrice(0);
                }
            }

            System.out.println("Writing to JSON file");
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), carSimuscastDto);
            System.out.println("Data written to " + outputFile);

        } catch (IOException e) {
            System.err.println("Error while converting HTML to JSON");
            e.printStackTrace();
        }
    }
}
