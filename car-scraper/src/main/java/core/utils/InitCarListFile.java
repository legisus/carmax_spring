package core.utils;

import core.model.Car;
import lombok.extern.slf4j.Slf4j;
import utils_api.CarUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
public class InitCarListFile {
    public static List<Car> initCarList(List<Car> carListInit, String path) {
        try {
            if (carListInit == null) {
                if (CarUtils.loadCarsListFromJsonFile(path) != null)
                    carListInit = CarUtils.loadCarsListFromJsonFile(path);
            }
            return carListInit;
        } catch (Exception e) {
           log.error(e.getMessage());
            return Collections.emptyList();
        }
    }





}
