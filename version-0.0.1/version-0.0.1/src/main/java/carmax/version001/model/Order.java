package carmax.version001.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Component;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Order {
    @Id
    private Long id;
    private String orderNumber;
    private String date;
    private String status;
    private String paymentMethod;
    private String deliveryMethod;
    private String deliveryAddress;
    private String deliveryDate;
    private String deliveryTime;
    private String deliveryStatus;
    private String deliveryPrice;
    private String totalPrice;
    private String comment;
    private User owner;
    private Car car;
}
