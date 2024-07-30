package scanner.utils;

import core.model.Car;
import lombok.extern.slf4j.Slf4j;
import utils_api.CarUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Slf4j
public class InitCarListFile {
//    public static List<Car> initCarList(List<Car> carListInit, String path) {
//        try {
//            if (carListInit == null) {
//                if (CarUtils.loadCarsListFromJsonFile(path) != null)
//                    carListInit = CarUtils.loadCarsListFromJsonFile(path);
//            }
//            return carListInit;
//        } catch (Exception e) {
//           log.error(e.getMessage());
//            return Collections.emptyList();
//        }
//    }

    public static List<Car> initCarList(List<Car> carListInit, String path) {
        try {
            Path filePath = Paths.get(path);

            // Check if the file exists
            if (Files.notExists(filePath)) {
                log.info("File not found, creating new file: " + path);

                // Create the file and initialize it with an empty list
                Files.createDirectories(filePath.getParent());
                Files.createFile(filePath);

                // Save an empty list to the file to initialize it
                CarUtils.saveCarsListToJsonFile(Collections.emptyList(), path);
            }

            // If carListInit is null, load the car list from the file
            if (carListInit == null) {
                carListInit = CarUtils.loadCarsListFromJsonFile(path);
            }

            return carListInit;
        } catch (IOException e) {
            log.error("Failed to create or load file: " + e.getMessage());
            return Collections.emptyList();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Collections.emptyList();
        }
    }
}
