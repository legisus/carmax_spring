package core.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "balance")
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="balance_id")
    private Long id;
    private Integer amount;
    private Integer buyingPower;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ToString.Exclude
    private User user;
}
