package scanner.utils;

import core.model.Car;
import core.model.enums.Drive;
import core.model.enums.Transmission;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CarParserV2 {
    public static List<Car> parseCarsFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        List<Car> carList = new ArrayList<>();

        try {
            if (html.contains("lane-content")) {
                log.info("Post-auction parsing");
                carList = parsePostAuctionHtml(doc);
            }

            return carList;
        } catch (Exception e) {
            log.error("Error parsing cars from html: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static List<Car> parsePostAuctionHtml(Document doc) {
        log.info("Parsing post-auction HTML");
        List<Car> carList = new ArrayList<>();
        Elements rows = doc.select("tr[data-cy-item-num]");

        for (Element row : rows) {
            Car car = new Car();

            log.info("Parsing run number, year, make, model, trim");
            String ymmtText = row.select(".item-info-row a.vc-details").text();
            String[] ymmtParts = ymmtText.split("- ", 2);

            if (ymmtParts.length == 2) {
                car.setRunNumber(extractRunNumber(ymmtParts[0]));
                String[] carDetails = ymmtParts[1].split(" ", 4);

                if (carDetails.length >= 3) {
                    car.setYear(parseIntSafe(carDetails[0]));
                    car.setMake(carDetails[1]);
                    car.setModel(carDetails[2]);
                    if (carDetails.length > 3) {
                        car.setTrim(carDetails[3]);
                    }
                }
            }

            log.info("Parsing color and mileage");
            String color = row.select(".mileage-row span.block").first().text();
            String mileageText = row.select(".mileage-row").text().replaceAll("[^\\d]", "");
            car.setColor(color);
            car.setMileage(parseIntSafe(mileageText));

            log.info("Parsing sold price");
            String soldPriceText = row.select(".price-row span").text().replaceAll("[^\\d]", "");
            if (!soldPriceText.isEmpty()) {
                car.setSoldPrice(parseIntSafe(soldPriceText));
            }

            carList.add(car);
        }

        return carList;
    }

    private static int extractRunNumber(String text) {
        text = text.replace("#", "").trim();
        return parseIntSafe(text);
    }

    private static int parseIntSafe(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            log.error("Error parsing integer from text: " + text);
            return 0; // or any other default value
        }
    }
}
