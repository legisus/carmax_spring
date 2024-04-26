package carmax.version001.repository;

import carmax.version001.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car save(Car car);
    Car update(Car car);
    void delete(Car car);
    Optional<Car> findById(Long id);
    Optional<Car> findCarByVin(String vin);
    Optional<List<Car>> findCarsByMillage(Integer millage);
}
