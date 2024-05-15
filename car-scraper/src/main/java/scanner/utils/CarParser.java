package scanner.utils;

import core.model.Car;
import core.model.enums.Drive;
import core.model.enums.Transmission;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
public class CarParser {
    public static Car parseCarFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        Car car = new Car();

        // Parsing year, make, model
        String ymmtText = doc.select(".vehicle-ymmt-I4Jge button").text();
        String[] ymmtParts = ymmtText.split(" ", 4); // Split into at least 4 parts, assuming Year Make Model Trim

        try{
            if (ymmtParts.length >= 3) {
                car.setYear(Integer.parseInt(ymmtParts[0]));
                car.setMake(ymmtParts[1]);
                car.setModel(ymmtParts[2]);
                // If a trim is present, set it, otherwise default to null which is already set in Car constructor
                if (ymmtParts.length > 3) {
                    car.setTrim(ymmtParts[3]);
                }
            }

            // Parsing mileage
            String mileageText = doc.select(".vehicle-mileage-aQs6j").text().replaceAll("[^\\d]", "");
            car.setMileage(Integer.parseInt(mileageText));

            // Parsing transmission and engine information
            String transmissionInfo = doc.select(".vehicle-transmission-KDK3z").text();
            parseTransmissionInfo(transmissionInfo, car);

            // Setting VIN
            car.setVin(doc.select(".vehicle-vin-Mc8Le").text());

            // Parsing defects
            String defects = doc.select(".vehicle-announcement-IRvfI hzn-text").text();
            car.setDefects(defects);

            // Additional parsing for color, estimatePrice, soldPrice, dateSold, location would go here

            return car;

        }catch (Exception e) {
            log.error("Error parsing car from html: " + e.getMessage());
            return null;
        }

    }

    private static void parseTransmissionInfo(String transmissionInfo, Car car) {
        String[] parts = transmissionInfo.split(" • ");

        // Flags to check if certain properties are set
        boolean engineSet = false, transmissionSet = false, driveSet = false;

        for (String part : parts) {
            part = part.trim();

            // Checking for engine size, indicated by a 'L'
            if (part.toUpperCase().endsWith("L") && !engineSet) {
                car.setEngine(part);
                engineSet = true;
            }

            // Checking for transmission type
            else if ((part.equalsIgnoreCase("automatic") || part.equalsIgnoreCase("manual")) && !transmissionSet) {
                car.setTransmission(Transmission.fromString(part));
                transmissionSet = true;
            }

            // Checking for drive type
            else if ((part.equalsIgnoreCase("2WD") || part.equalsIgnoreCase("4WD")) && !driveSet) {
                car.setDrive(Drive.fromString(part));
                driveSet = true;
            }
        }

        // Set to NA if not set
        if (!engineSet) {
            car.setEngine("n/a");
        }
        if (!transmissionSet) {
            car.setTransmission(Transmission.NA);
        }
        if (!driveSet) {
            car.setDrive(Drive.NA);
        }
    }
}
