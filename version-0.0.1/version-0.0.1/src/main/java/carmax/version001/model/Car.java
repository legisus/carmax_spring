package carmax.version001.model;


import carmax.version001.model.carInner.Drive;
import carmax.version001.model.carInner.Transmission;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Car {
    private Long id;
    private String vin;
    private Integer year;
    private String make;
    private String model;
    private String trim;
    private Drive drive = Drive.NA;
    private Transmission transmission = Transmission.NA;
    private String engine;
    private String color;
    private Integer millage;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vin", referencedColumnName = "vin")
    private Estimation estimation;
    private Integer soldPrice;
    private String dateOfAuction;
    private String location;
    private Integer runNumber;
    private String lane;
    private String defects;
}
