package carmax.version001.model;


import carmax.version001.model.carInner.Drive;
import carmax.version001.model.carInner.Transmission;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
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
    private Integer millage;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", referencedColumnName = "id")
    private Estimation estimation;

    private Integer soldPrice;
    private String dateOfAuction;
    private String location;
    private Integer runNumber;
    private String lane;
    private String defects;

    @ManyToMany(mappedBy = "mySelectedCars", cascade = CascadeType.ALL)
    private Set<User> usersSelected = new HashSet<>();

    @ManyToOne
//    @JoinColumn(name = "owner_id")
    @JoinColumn(name = "user_id")
    private User owner;

}
