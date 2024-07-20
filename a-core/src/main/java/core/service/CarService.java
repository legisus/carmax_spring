package core.service;

import core.model.Car;

import java.util.List;

public interface CarService {

    Car addOrUpdateCar(Car car);

    Car updateCar(Car car); //deprecated

    void deleteCar(Car car);

    List<Car> getAll();

    Car getByVin(String vin);

//    Car addOrUpdateCar(Car car);

}
