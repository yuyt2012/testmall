package io.ecp.testmall.delivery.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

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
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @PrePersist
    protected void onRegDate() {
        regDate = new Date();
        updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdateDate() {
        updateDate = new Date();
    }
}
