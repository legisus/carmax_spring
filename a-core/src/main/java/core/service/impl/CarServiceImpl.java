package core.service.impl;

import core.model.Car;
import core.service.CarService;
import core.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addOrUpdateCar(Car car) {
        Optional<Car> existingCarOptional = carRepository.findCarByVin(car.getVin());

        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();
            boolean needsUpdate = false;

            if (!existingCar.equals(car)) {
                needsUpdate = true;
                existingCar.setYear(car.getYear());
                existingCar.setMake(car.getMake());
                existingCar.setModel(car.getModel());
                existingCar.setTrim(car.getTrim());
                existingCar.setDrive(car.getDrive());
                existingCar.setTransmission(car.getTransmission());
                existingCar.setEngine(car.getEngine());
                existingCar.setColor(car.getColor());
                existingCar.setMileage(car.getMileage());
                existingCar.setSoldPrice(car.getSoldPrice());
                existingCar.setRunNumber(car.getRunNumber());
                existingCar.setLane(car.getLane());
                existingCar.setDefects(car.getDefects());
                existingCar.setPicturesUrl(car.getPicturesUrl());
                existingCar.setAuction(car.getAuction());
                existingCar.setEstimation(car.getEstimation());
                existingCar.setUsersSelected(car.getUsersSelected());
                existingCar.setOwner(car.getOwner());
                existingCar.setBids(car.getBids());
            }

            if (needsUpdate) {
                return carRepository.save(existingCar);
            } else {
                return existingCar;
            }
        } else {
            return carRepository.save(car);
        }
    }

    @Override
    public Car updateCar(Car car) { //deprecated
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
