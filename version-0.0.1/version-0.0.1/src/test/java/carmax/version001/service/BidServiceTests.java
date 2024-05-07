package carmax.version001.service;

import carmax.version001.model.Bid;
import carmax.version001.model.Car;
import carmax.version001.model.Role;
import carmax.version001.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BidServiceTests {

    @Autowired
    private BidService bidService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    @Test
    @Order(1)
    void addBidTest() {
        Bid bid = new Bid();
        bid.setBidPrice(100);
        bid.setStatus(true);

        Bid bid2 = new Bid();
        bid2.setBidPrice(200);
        bid2.setStatus(true);

        Bid savedBid = bidService.addBid(bid);
        Bid savedBid2 = bidService.addBid(bid2);

        assertNotNull(savedBid, "Saved bid should not be null");
        assertNotNull(savedBid2, "Saved bid2 should not be null");
    }

    @Test
    @Order(2)
    void updateBidTest() {
        Bid bid = bidService.findByBidPrice(100).get(0);
        bid.setStatus(false);
        Bid updatedBid = bidService.updateBid(bid);
        assertFalse(updatedBid.isStatus());
    }

    @Test
    @Order(3)
    void findByBidPriceTest() {
        Integer bidPrice = 100;
        List<Bid> bids = bidService.findByBidPrice(bidPrice);
        assertFalse(bids.isEmpty(), "Bids list should not be empty");
    }

    @Test
    @Order(4)
    void findByStatusTest() {
        boolean status = true;
        List<Bid> bids = bidService.findByStatus(status);
        assertFalse(bids.isEmpty(), "Bids list should not be empty");
    }

    @Test
    @Order(5)
    void findByUserTest() {

        Role role = new Role();
        role.setName("USER");
        roleService.create(role);

        User user = new User();
        user.setRole(role);
        user.setFirstName("Jonn");
        user.setLastName("Smith");
        user.setEmail("j.smith@gmail.com");
        user.setPassword("H1b3rn14n!");
        user.setBids(bidService.findByBidPrice(200));
        userService.create(user);

        List<Bid> bids = bidService.findByUser(user);
        assertFalse(bids.isEmpty(), "Bids list should not be empty");
    }

    @Test
    @Order(6)
    void findByCarTest() {
        Car car = new Car();
        car.setMake("Toyota");
        car.setModel("Corolla");
        car.setYear(2015);
        car.setMileage(10000);
        car.setBids(bidService.findByBidPrice(200));
        carService.addCar(car);

        List<Bid> bids = bidService.findByCar(car);
        assertFalse(bids.isEmpty(), "Bids list should not be empty");
    }

    @Test
    @Order(7)
    void deleteBidTest() {
        Bid bid = bidService.findByBidPrice(100).get(0);
        bidService.deleteBid(bid);
        List<Bid> bids = bidService.findByBidPrice(100);
        assertTrue(bids.isEmpty(), "Bids list should be empty");
    }
}