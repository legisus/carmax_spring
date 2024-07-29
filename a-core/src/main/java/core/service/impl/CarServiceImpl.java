package core.service.impl;

import core.model.Car;
import core.model.Estimation;
import core.service.CarService;
import core.repository.CarRepository;
import core.service.EstimationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final EstimationService estimationService;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, EstimationService estimationService) {
        this.carRepository = carRepository;
        this.estimationService = estimationService;
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

    public void updateJDPowerEstimation(Car car) {
        try {
            if (car.getEstimation() == null) {
                log.info("Estimation is null for car VIN: {}", car.getVin());
                Estimation newEstimation = new Estimation();
                // Update the new estimation here
                car.setEstimation(estimationService.addOrUpdateEstimation(newEstimation));
            } else {
                Estimation estimation = car.getEstimation();
                if (estimation.getEstimationJDPower() == null) {
                    log.info("EstimationJDPower is null for car VIN: {}", car.getVin());
                    // Update the estimation here
                    estimationService.addOrUpdateEstimation(estimation);
                } else if (estimation.getEstimationJDPower() < 1) {
                    log.info("EstimationJDPower is less than 1 for car VIN: {}", car.getVin());
                    // Update the estimation here
                    estimationService.addOrUpdateEstimation(estimation);
                } else {
                    log.info("No Updates Needed for car VIN: {}", car.getVin());
                }
            }
            carRepository.save(car);
        } catch (Exception e) {
            log.error("Error updating estimation for car VIN: {}", car.getVin(), e);
        }
    }

    @Override
    public Optional<Car> findCarByDetails(Integer year, String make, String model, Integer mileage) {
        return carRepository.findByYearAndMakeAndModelAndMileage(year, make, model, mileage);
    }
}
