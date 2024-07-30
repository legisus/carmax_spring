package core.repository;

import core.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarByVin(String vin);
    Optional<List<Car>> findCarsByMileage(Integer mileage);
    Optional<Car> findByYearAndMakeAndModelAndMileage(Integer year, String make, String model, Integer mileage);
}
