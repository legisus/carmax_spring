package core.service;

import core.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    Car addOrUpdateCar(Car car);

    Car updateCar(Car car); //deprecated

    void deleteCar(Car car);

    List<Car> getAll();

    Car getByVin(String vin);

    Optional<Car> findCarByDetails(Integer year, String make, String model, Integer mileage);

    void updateSoldPriceIfValid(Integer year, String make, String model, Integer mileage, Integer soldPrice);

}
