package core.model;


import core.model.enums.Drive;
import core.model.enums.Transmission;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long id;

    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String trim;
    @Enumerated(EnumType.STRING)
    private Drive drive = Drive.NA;
    @Enumerated(EnumType.STRING)
    private Transmission transmission = Transmission.NA;
    private String engine;
    private String color;
    private Integer mileage;
    private Integer soldPrice;
    private Integer runNumber;
    private String lane;
    private String defects;
    private String picturesUrl;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estimation_id", referencedColumnName = "estimation_id")
    private Estimation estimation;

    @ManyToMany(mappedBy = "mySelectedCars", cascade = CascadeType.ALL)
    private Set<User> usersSelected = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "car")
    private List<Bid> bids;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Car {vin = %s, year = %d, make = %s, model = %s}", vin, year, make, model);
    }

}
