package carmax.version001.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Estimation {
    @Id
    private Long id;
    private String vin;
    private Integer estimationJDPower;
    private Integer estimationKBBPrivateParty;
    private Integer estimationKBBDealerRetail;
    private Integer estimationManheimMMR;
    private Integer estimatedRetailValue;

    @OneToOne(mappedBy = "estimation")
    private Car car;
}
