package core.model;

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
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @OneToOne(mappedBy = "user")
    private Balance balance;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_selected_cars",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),  // Corrected to match the @Column name
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "car_id")  // Assuming 'id' is the @Column name in Car entity
    )
    @ToString.Exclude
    private Set<Car> mySelectedCars = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @ToString.Exclude
    private Set<Car> boughtCars = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @ToString.Exclude
    private Set<Order> myOrders = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @ToString.Exclude
    private List<Bid> bids;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password);
    }
}



