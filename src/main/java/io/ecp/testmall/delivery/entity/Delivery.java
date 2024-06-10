package io.ecp.testmall.delivery.entity;

import io.ecp.testmall.member.entity.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;
    private String receiverName;
    private String receiverPhone;
    private String receiverCity;
    private String receiverStreet;
    private String receiverZipcode;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;
}
