package core.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.*;

import java.util.Objects;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode
@ToString
@Table(name = "estimations")
public class Estimation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estimation_id")
    private Long id;

    private Integer estimationJDPower;
    private Integer estimationKBBPrivateParty;
    private Integer estimationKBBDealerRetail;
    private Integer estimationManheimMMR;
    private Integer estimatedRetailValue;

    @ToString.Exclude
    @OneToOne(mappedBy = "estimation")
    private Car car;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Estimation estimation = (Estimation) o;
        return Objects.equals(id, estimation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
