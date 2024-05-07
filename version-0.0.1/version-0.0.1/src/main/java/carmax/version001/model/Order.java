package carmax.version001.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String orderNumber;
    private LocalDate date;
    private String status;
    private String paymentMethod;
    private String deliveryMethod;
    private String deliveryAddress;
    private LocalDate deliveryDate;
    private String deliveryStatus;
    private String deliveryPrice;
    private String totalPrice;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

}
