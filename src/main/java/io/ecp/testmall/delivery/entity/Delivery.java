package io.ecp.testmall.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;
    private String receiverName;
    private String receiverPhone;
    @Embedded
    private DeliveryAddress deliveryAddress;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
