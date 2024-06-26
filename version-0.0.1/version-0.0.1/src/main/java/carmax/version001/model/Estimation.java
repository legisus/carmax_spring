package carmax.version001.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "estimations")
public class Estimation {
    @Id
    private Long id;

    private Integer estimationJDPower;

    private Integer estimationKBBPrivateParty;

    private Integer estimationKBBDealerRetail;

    private Integer estimationManheimMMR;

    private Integer estimatedRetailValue;

    @ToString.Exclude
    @OneToOne(mappedBy = "estimation")
    private Car car;
}
