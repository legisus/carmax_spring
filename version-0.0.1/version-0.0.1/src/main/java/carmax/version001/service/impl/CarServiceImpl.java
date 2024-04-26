package carmax.version001.service.impl;

import carmax.version001.model.Car;
import carmax.version001.repository.CarRepository;
import carmax.version001.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(Car car) {
        if(Objects.equals(car.getVin(), carRepository.findCarByVin(car.getVin()).get().getVin())){
            return carRepository.save(car);
        }
        return null;
    }

    @Override
    public void deleteCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car getByVin(String vin) {
        return carRepository.findCarByVin(vin).get();
    }
}
