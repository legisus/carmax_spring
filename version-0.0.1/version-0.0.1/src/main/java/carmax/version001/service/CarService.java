package carmax.version001.service;

import carmax.version001.model.Car;

import java.util.List;

public interface CarService {

    Car addCar(Car car);

    Car updateCar(Car car);

    void deleteCar(Car car);

    List<Car> getAll();

    Car getByVin(String vin);

}
