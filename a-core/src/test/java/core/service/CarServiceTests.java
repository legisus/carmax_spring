package core.service;

import core.model.Auction;
import core.model.Car;
import core.model.enums.Drive;
import core.model.enums.Locations;
import core.model.enums.Transmission;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarServiceTests {

    private final CarService carService;

    @Autowired
    public CarServiceTests(CarService carService) {
        this.carService = carService;
    }

    @Test
    @Order(1)
    void createCarTest() {

        Auction auction = new Auction();
//        auction.setDateTimeOfAuction(LocalDateTime.now());
        auction.setLocation(Locations.BOISE);

        Car car = new Car();
        car.setVin("1HGBH41JXMN109186");
        car.setYear(2019);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setTrim("LE");
        car.setDrive(Drive.WD4);
        car.setTransmission(Transmission.AUTOMATIC);
        car.setColor("Blue");
        car.setMileage(32000);
        car.setAuction(auction);
        car.setSoldPrice(15000);

        Car savedCar = carService.addOrUpdateCar(car);
        assertNotNull(savedCar.getId(), "Car should have an id after being saved");
        assertEquals(car.getVin(), savedCar.getVin(), "VIN should match");
    }

    @Test
    @Order(2)
    void readCarTest() {
        String vin = "1HGBH41JXMN109186";
        Car car = carService.getByVin(vin);
        assertEquals("1HGBH41JXMN109186", car.getVin(), "VIN should match");
        assertEquals(2019, car.getYear(), "Year should match");
    }

    @Test
    @Order(3)
    void updateCarTest() {
        String vin = "1HGBH41JXMN109186";
        Car car = carService.getByVin(vin);
        car.setColor("Red");
        car.setMileage(35000);

        Car updatedCar = carService.addOrUpdateCar(car);
        assertEquals("Red", updatedCar.getColor(), "Color should be updated");
        assertEquals(35000, updatedCar.getMileage(), "Mileage should be updated");
    }

    @Test
    @Order(4)
    void getAllCarsTest() {
        Auction auction = new Auction();
//        auction.setDateTimeOfAuction(LocalDateTime.now());
        auction.setLocation(Locations.BOISE);

        Car car = new Car();
        car.setVin("1HGBH41JXMN109187");
        car.setYear(2019);
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setTrim("LE");
        car.setDrive(Drive.WD4);
        car.setTransmission(Transmission.AUTOMATIC);
        car.setColor("Blue");
        car.setMileage(32000);
        car.setAuction(auction);
        car.setSoldPrice(15000);

        carService.addOrUpdateCar(car);

        assertTrue(carService.getAll().size() >= 2, "There should be minimum 2 cars in the database");
    }

    @Test
    @Order(5)
    void deleteCarTest() {
        String vin = "1HGBH41JXMN109186";
        Car carForDeletion = carService.getByVin(vin);
        carService.deleteCar(carForDeletion);
        Car carDeleted = carService.getByVin(vin);
        assertNull(carDeleted, "Car should be null after deletion");
    }
}
