package carmax.version001.service.impl;

import carmax.version001.model.Car;
import carmax.version001.repository.CarRepository;
import carmax.version001.service.CarService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional
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
        if (carRepository.findCarByVin(car.getVin()).isPresent()) {
            return carRepository.save(car);
        }
        return null; // Or throw an exception if you prefer
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
        return carRepository.findCarByVin(vin).orElse(null); // Return null or handle it differently
    }
}
