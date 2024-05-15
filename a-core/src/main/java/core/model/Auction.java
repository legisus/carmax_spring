package core.model;

import core.model.enums.Locations;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auction_id")
    private Long id;

    private LocalDate dateOfAuction;
    private LocalTime timeOfAuction;
    private Locations location;

    @OneToMany(mappedBy = "auction")
    private Set<Car> cars;
}
